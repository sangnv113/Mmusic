
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

import com.example.WebProject.entity.Drum;
import com.example.WebProject.entity.Ma;
import com.example.WebProject.model.DrumInfo;
import com.example.WebProject.service.CategoryService;
import com.example.WebProject.service.ColorService;
import com.example.WebProject.service.DrumService;
import com.example.WebProject.service.MaService;
import com.example.WebProject.service.ProducerService;

@Transactional
@Repository
public class DrumDao {
	@Autowired
	private DrumService drumService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ColorService colorService;
	@Autowired

	private ProducerService producerService;

	@Autowired

	private MaService maService;

	// xu ly in tien
	public DrumInfo findDrumInfo(int id) {

		try {
			Drum gt = drumService.findOne(id);
			if (gt == null) {
				return null;
			}

			DrumInfo gtinfo = new DrumInfo(gt.getId(), gt.getName(), gt.getCategoryid().getCategory(),
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
	public DrumInfo findDrumInfoSave(int id) {

		try {
			Drum gt = drumService.findOne(id);
			if (gt == null) {
				return null;
			}
			DrumInfo gtinfo = new DrumInfo(gt.getId(), gt.getName(), gt.getCategoryid().getCategory(),
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
	public List<DrumInfo> findAllDrumInfo() {

		try {
			List<DrumInfo> listdrum = new ArrayList<DrumInfo>();
			List<Drum> drum = (List<Drum>) drumService.findAll();

			Drum gt = new Drum();
			DrumInfo gtinfo;

			for (int i = 0; i < drum.size(); i++) {
				gt = drum.get(i);
				gtinfo = new DrumInfo(gt.getId(), gt.getName(), gt.getCategoryid().getCategory(),
						gt.getProducerid().getName(), gt.getColorid().getName(), (int) gt.getRate(), gt.getSoluong(),
						GuitarDao.intien(gt.getGia()), gt.getSoluot(), gt.getGiamgia(),
						GuitarDao.intien(gt.getGiasaugiam()));
				if (gt.getDatepr() != null) {
					gtinfo.setDatepr(gt.getDatepr().toString());
				}

				listdrum.add(gtinfo);

			}

			return listdrum;
		} catch (NoResultException e) {
			return null;
		}
	}

	// find for index.html
	public List<DrumInfo> listDrumInfo() {

		try {
			List<DrumInfo> listdrum = new ArrayList<DrumInfo>();
			List<Drum> drum = (List<Drum>) drumService.findAll();

			Drum gt = new Drum();
			DrumInfo gtinfo;

			for (int i = drum.size() - 1; i >= drum.size() - 4; i--) {

				if (i < 0) {
					return listdrum;
				}
				gt = drum.get(i);
				if (gt == null) {
					return listdrum;
				}
				gtinfo = new DrumInfo(gt.getId(), gt.getName(), gt.getCategoryid().getCategory(),
						gt.getProducerid().getName(), gt.getColorid().getName(), (int) gt.getRate(), gt.getSoluong(),
						GuitarDao.intien(gt.getGia()), gt.getSoluot(), gt.getGiamgia(),
						GuitarDao.intien(gt.getGiasaugiam()));
				if (gt.getDatepr() != null) {
					gtinfo.setDatepr(gt.getDatepr().toString());
				}

				listdrum.add(gtinfo);

			}

			return listdrum;
		} catch (NoResultException e) {
			return null;
		}
	}

	public void save(DrumInfo drumInfo) {
		// create object Drum from druminfo
		final byte[] drumImage = drumService.findOne(drumInfo.getId()).getImage();
		Drum gt = new Drum(drumInfo.getId(), drumInfo.getName(),
				categoryService.findByCategoryContaining(drumInfo.getCategory()).get(0),
				producerService.findByNameContaining(drumInfo.getProducer()).get(0),
				colorService.findByNameContaining(drumInfo.getColor()).get(0), drumInfo.getSoluong(),
				drumInfo.getGia(), drumInfo.getGiamgia(),
				GuitarDao.GiaSauGiam(Integer.parseInt(drumInfo.getGia()), drumInfo.getGiamgia()));
		// set date
		if (drumInfo.getDatepr().toString().trim().equals("") == false) {

			String date = drumInfo.getDatepr().toString();
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

		if (drumInfo.getFileData().isEmpty() == false) {
			byte[] image = null;
			try {
				image = drumInfo.getFileData().getBytes();
			} catch (IOException e) {
			}
			if (image != null && image.length > 0) {
				gt.setImage(image);
			}
		} else
			gt.setImage(drumImage);

		drumService.save(gt);

	}

	public void SaveCreate(DrumInfo drumInfo) {
		Ma ma = maService.findOne(1);
		// int entity Drum from drumInfo-non gianiemyet, rate, status
		Drum gt = new Drum(ma.getDrum(), drumInfo.getName(),
				categoryService.findByCategoryContaining(drumInfo.getCategory()).get(0),
				producerService.findByNameContaining(drumInfo.getProducer()).get(0),
				colorService.findByNameContaining(drumInfo.getColor()).get(0), 0, 0, drumInfo.getSoluong(),
				drumInfo.getGia(), drumInfo.getGiamgia(),
				GuitarDao.GiaSauGiam(Integer.parseInt(drumInfo.getGia()), drumInfo.getGiamgia()), 0);
		// set date
		if (drumInfo.getDatepr().toString().trim().equals("") == false) {

			String date = drumInfo.getDatepr().toString();
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
		if (drumInfo.getFileData() != null) {
			byte[] image = null;
			try {
				image = drumInfo.getFileData().getBytes();
			} catch (IOException e) {
			}
			if (image != null && image.length > 0) {
				gt.setImage(image);
			}
		}

		drumService.save(gt);

		Ma vma=new Ma(1,ma.getGuitar(), ma.getPiano(), ma.getUkulele(),
  				ma.getDrum()+1, ma.getFlute(), ma.getAccessory(), ma.getProducer(),
  				ma.getColor() );	
  		maService.save(vma);

	}

}
