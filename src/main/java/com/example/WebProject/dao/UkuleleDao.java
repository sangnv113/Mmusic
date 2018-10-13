
package com.example.WebProject.dao;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.WebProject.entity.Ukulele;
import com.example.WebProject.entity.Ma;
import com.example.WebProject.model.UkuleleInfo;
import com.example.WebProject.service.CategoryService;
import com.example.WebProject.service.ColorService;
import com.example.WebProject.service.UkuleleService;
import com.example.WebProject.service.MaService;
import com.example.WebProject.service.ProducerService;

@Transactional
@Repository
public class UkuleleDao {
	@Autowired
	private UkuleleService ukuleleService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ColorService colorService;
	@Autowired

	private ProducerService producerService;

	@Autowired

	private MaService maService;

	// xu ly in tien
	public UkuleleInfo findUkuleleInfo(int id) {

		try {
			Ukulele gt = ukuleleService.findOne(id);
			if (gt == null) {
				return null;
			}

			UkuleleInfo gtinfo = new UkuleleInfo(gt.getId(), gt.getName(), gt.getCategoryid().getCategory(),
					gt.getProducerid().getName(), gt.getColorid().getName(), (int) gt.getRate(), gt.getSoluong(),
					GuitarDao.intien(gt.getGia()), gt.getSoluot(), gt.getGiamgia(),
					GuitarDao.intien(gt.getGiasaugiam()), gt.getVisits());
			if (gt.getDatepr() != null) {
				gtinfo.setDatepr(gt.getDatepr().toString());
			}

			return gtinfo;
		} catch (NoResultException e) {
			return null;
		}
	}

	// khong xu ly in tien
	public UkuleleInfo findUkuleleInfoSave(int id) {

		try {
			Ukulele gt = ukuleleService.findOne(id);
			if (gt == null) {
				return null;
			}
			UkuleleInfo gtinfo = new UkuleleInfo(gt.getId(), gt.getName(), gt.getCategoryid().getCategory(),
					gt.getProducerid().getName(), gt.getColorid().getName(), (int) gt.getRate(), gt.getSoluong(),
					gt.getGia(), gt.getSoluot(), gt.getGiamgia(), gt.getGiasaugiam());
			if (gt.getDatepr() != null) {
				gtinfo.setDatepr(gt.getDatepr().toString());
			}

			return gtinfo;
		} catch (NoResultException e) {
			return null;
		}
	}

	// find all
	public List<UkuleleInfo> findAllUkuleleInfo() {

		try {
			List<UkuleleInfo> listukulele = new ArrayList<UkuleleInfo>();
			List<Ukulele> ukulele = (List<Ukulele>) ukuleleService.findAll();

			Ukulele gt = new Ukulele();
			UkuleleInfo gtinfo;

			for (int i = 0; i < ukulele.size(); i++) {
				gt = ukulele.get(i);
				gtinfo = new UkuleleInfo(gt.getId(), gt.getName(), gt.getCategoryid().getCategory(),
						gt.getProducerid().getName(), gt.getColorid().getName(), (int) gt.getRate(), gt.getSoluong(),
						GuitarDao.intien(gt.getGia()), gt.getSoluot(), gt.getGiamgia(),
						GuitarDao.intien(gt.getGiasaugiam()));
				if (gt.getDatepr() != null) {
					gtinfo.setDatepr(gt.getDatepr().toString());
				}

				listukulele.add(gtinfo);

			}

			return listukulele;
		} catch (NoResultException e) {
			return null;
		}
	}

	// find for index.html
	public List<UkuleleInfo> listUkuleleInfo() {

		try {
			List<UkuleleInfo> listukulele = new ArrayList<UkuleleInfo>();
			List<Ukulele> ukulele = (List<Ukulele>) ukuleleService.findAll();

			Ukulele gt = new Ukulele();
			UkuleleInfo gtinfo;

			for (int i = ukulele.size() - 1; i >= ukulele.size() - 4; i--) {

				if (i < 0) {
					return listukulele;
				}
				gt = ukulele.get(i);
				if (gt == null) {
					return listukulele;
				}
				gtinfo = new UkuleleInfo(gt.getId(), gt.getName(), gt.getCategoryid().getCategory(),
						gt.getProducerid().getName(), gt.getColorid().getName(), (int) gt.getRate(), gt.getSoluong(),
						GuitarDao.intien(gt.getGia()), gt.getSoluot(), gt.getGiamgia(),
						GuitarDao.intien(gt.getGiasaugiam()));
				if (gt.getDatepr() != null) {
					gtinfo.setDatepr(gt.getDatepr().toString());
				}

				listukulele.add(gtinfo);

			}

			return listukulele;
		} catch (NoResultException e) {
			return null;
		}
	}

	public void save(UkuleleInfo ukuleleInfo) {
		// create object Ukulele from ukuleleinfo
		final byte[] ukuleleImage = ukuleleService.findOne(ukuleleInfo.getId()).getImage();
		Ukulele gt = new Ukulele(ukuleleInfo.getId(), ukuleleInfo.getName(),
				categoryService.findByCategoryContaining(ukuleleInfo.getCategory()).get(0),
				producerService.findByNameContaining(ukuleleInfo.getProducer()).get(0),
				colorService.findByNameContaining(ukuleleInfo.getColor()).get(0), ukuleleInfo.getSoluong(),
				ukuleleInfo.getGia(), ukuleleInfo.getGiamgia(),
				GuitarDao.GiaSauGiam(Integer.parseInt(ukuleleInfo.getGia()), ukuleleInfo.getGiamgia()));
		// set date
		if (ukuleleInfo.getDatepr().toString().trim().equals("") == false) {

			String date = ukuleleInfo.getDatepr().toString();
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date startDate;
			try {
				startDate = sdf.parse(date);
				java.sql.Date sqlDate = new java.sql.Date(startDate.getTime());
				gt.setDatepr(sqlDate);
				/* System.out.println("ok"); */

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else
			gt.setDatepr(null);
		// set image

		if (ukuleleInfo.getFileData().isEmpty() == false) {
			byte[] image = null;
			try {
				image = ukuleleInfo.getFileData().getBytes();
			} catch (IOException e) {
			}
			if (image != null && image.length > 0) {
				gt.setImage(image);
			}
		} else
			gt.setImage(ukuleleImage);

		ukuleleService.save(gt);

	}

	public void SaveCreate(UkuleleInfo ukuleleInfo) {
		Ma ma = maService.findOne(1);
		// int entity Ukulele from ukuleleInfo-non gianiemyet, rate, status
		Ukulele gt = new Ukulele(ma.getUkulele(), ukuleleInfo.getName(),
				categoryService.findByCategoryContaining(ukuleleInfo.getCategory()).get(0),
				producerService.findByNameContaining(ukuleleInfo.getProducer()).get(0),
				colorService.findByNameContaining(ukuleleInfo.getColor()).get(0), 0, 0, ukuleleInfo.getSoluong(),
				ukuleleInfo.getGia(), ukuleleInfo.getGiamgia(),
				GuitarDao.GiaSauGiam(Integer.parseInt(ukuleleInfo.getGia()), ukuleleInfo.getGiamgia()), 0);
		// set date
		if (ukuleleInfo.getDatepr().toString().trim().equals("") == false) {

			String date = ukuleleInfo.getDatepr().toString();
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date startDate;
			try {
				startDate = sdf.parse(date);
				java.sql.Date sqlDate = new java.sql.Date(startDate.getTime());
				gt.setDatepr(sqlDate);
				/* System.out.println("ok"); */

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else
			gt.setDatepr(null);
		// set image
		if (ukuleleInfo.getFileData() != null) {
			byte[] image = null;
			try {
				image = ukuleleInfo.getFileData().getBytes();
			} catch (IOException e) {
			}
			if (image != null && image.length > 0) {
				gt.setImage(image);
			}
		}

		ukuleleService.save(gt);

		Ma vma=new Ma(1,ma.getGuitar(), ma.getPiano(), ma.getUkulele()+1,
  				ma.getDrum(), ma.getFlute(), ma.getAccessory(), ma.getProducer(),
  				ma.getColor() );	
  		maService.save(vma);

	}

}
