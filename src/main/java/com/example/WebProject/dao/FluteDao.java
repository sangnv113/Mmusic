
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

import com.example.WebProject.entity.Flute;
import com.example.WebProject.entity.Ma;
import com.example.WebProject.model.FluteInfo;
import com.example.WebProject.service.CategoryService;
import com.example.WebProject.service.ColorService;
import com.example.WebProject.service.FluteService;
import com.example.WebProject.service.MaService;
import com.example.WebProject.service.ProducerService;

@Transactional
@Repository
public class FluteDao {
	@Autowired
	private FluteService fluteService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ColorService colorService;
	@Autowired

	private ProducerService producerService;

	@Autowired

	private MaService maService;

	// xu ly in tien
	public FluteInfo findFluteInfo(int id) {

		try {
			Flute gt = fluteService.findOne(id);
			if (gt == null) {
				return null;
			}

			FluteInfo gtinfo = new FluteInfo(gt.getId(), gt.getName(), gt.getCategoryid().getCategory(),
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
	public FluteInfo findFluteInfoSave(int id) {

		try {
			Flute gt = fluteService.findOne(id);
			if (gt == null) {
				return null;
			}
			FluteInfo gtinfo = new FluteInfo(gt.getId(), gt.getName(), gt.getCategoryid().getCategory(),
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
	public List<FluteInfo> findAllFluteInfo() {

		try {
			List<FluteInfo> listflute = new ArrayList<FluteInfo>();
			List<Flute> flute = (List<Flute>) fluteService.findAll();

			Flute gt = new Flute();
			FluteInfo gtinfo;

			for (int i = 0; i < flute.size(); i++) {
				gt = flute.get(i);
				gtinfo = new FluteInfo(gt.getId(), gt.getName(), gt.getCategoryid().getCategory(),
						gt.getProducerid().getName(), gt.getColorid().getName(), (int) gt.getRate(), gt.getSoluong(),
						GuitarDao.intien(gt.getGia()), gt.getSoluot(), gt.getGiamgia(),
						GuitarDao.intien(gt.getGiasaugiam()));
				if (gt.getDatepr() != null) {
					gtinfo.setDatepr(gt.getDatepr().toString());
				}

				listflute.add(gtinfo);

			}

			return listflute;
		} catch (NoResultException e) {
			return null;
		}
	}

	// find for index.html
	public List<FluteInfo> listFluteInfo() {

		try {
			List<FluteInfo> listflute = new ArrayList<FluteInfo>();
			List<Flute> flute = (List<Flute>) fluteService.findAll();

			Flute gt = new Flute();
			FluteInfo gtinfo;

			for (int i = flute.size() - 1; i >= flute.size() - 4; i--) {

				if (i < 0) {
					return listflute;
				}
				gt = flute.get(i);
				if (gt == null) {
					return listflute;
				}
				gtinfo = new FluteInfo(gt.getId(), gt.getName(), gt.getCategoryid().getCategory(),
						gt.getProducerid().getName(), gt.getColorid().getName(), (int) gt.getRate(), gt.getSoluong(),
						GuitarDao.intien(gt.getGia()), gt.getSoluot(), gt.getGiamgia(),
						GuitarDao.intien(gt.getGiasaugiam()));
				if (gt.getDatepr() != null) {
					gtinfo.setDatepr(gt.getDatepr().toString());
				}

				listflute.add(gtinfo);

			}

			return listflute;
		} catch (NoResultException e) {
			return null;
		}
	}

	public void save(FluteInfo fluteInfo) {
		// create object Flute from fluteinfo
		final byte[] fluteImage = fluteService.findOne(fluteInfo.getId()).getImage();
		Flute gt = new Flute(fluteInfo.getId(), fluteInfo.getName(),
				categoryService.findByCategoryContaining(fluteInfo.getCategory()).get(0),
				producerService.findByNameContaining(fluteInfo.getProducer()).get(0),
				colorService.findByNameContaining(fluteInfo.getColor()).get(0), fluteInfo.getSoluong(),
				fluteInfo.getGia(), fluteInfo.getGiamgia(),
				GuitarDao.GiaSauGiam(Integer.parseInt(fluteInfo.getGia()), fluteInfo.getGiamgia()));
		// set date
		if (fluteInfo.getDatepr().toString().trim().equals("") == false) {

			String date = fluteInfo.getDatepr().toString();
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

		if (fluteInfo.getFileData().isEmpty() == false) {
			byte[] image = null;
			try {
				image = fluteInfo.getFileData().getBytes();
			} catch (IOException e) {
			}
			if (image != null && image.length > 0) {
				gt.setImage(image);
			}
		} else
			gt.setImage(fluteImage);

		fluteService.save(gt);

	}

	public void SaveCreate(FluteInfo fluteInfo) {
		Ma ma = maService.findOne(1);
		// int entity Flute from fluteInfo-non gianiemyet, rate, status
		Flute gt = new Flute(ma.getFlute(), fluteInfo.getName(),
				categoryService.findByCategoryContaining(fluteInfo.getCategory()).get(0),
				producerService.findByNameContaining(fluteInfo.getProducer()).get(0),
				colorService.findByNameContaining(fluteInfo.getColor()).get(0), 0, 0, fluteInfo.getSoluong(),
				fluteInfo.getGia(), fluteInfo.getGiamgia(),
				GuitarDao.GiaSauGiam(Integer.parseInt(fluteInfo.getGia()), fluteInfo.getGiamgia()), 0);
		// set date
		if (fluteInfo.getDatepr().toString().trim().equals("") == false) {

			String date = fluteInfo.getDatepr().toString();
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
		if (fluteInfo.getFileData() != null) {
			byte[] image = null;
			try {
				image = fluteInfo.getFileData().getBytes();
			} catch (IOException e) {
			}
			if (image != null && image.length > 0) {
				gt.setImage(image);
			}
		}

		fluteService.save(gt);


		Ma vma=new Ma(1,ma.getGuitar(), ma.getPiano(), ma.getUkulele(),
  				ma.getDrum(), ma.getFlute()+1, ma.getAccessory(), ma.getProducer(),
  				ma.getColor() );		
  		maService.save(vma);

	}

}
