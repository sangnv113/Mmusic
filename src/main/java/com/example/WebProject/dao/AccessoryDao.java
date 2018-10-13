
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

import com.example.WebProject.entity.Accessory;
import com.example.WebProject.entity.Ma;
import com.example.WebProject.model.AccessoryInfo;
import com.example.WebProject.service.CategoryService;
import com.example.WebProject.service.ColorService;
import com.example.WebProject.service.AccessoryService;
import com.example.WebProject.service.MaService;
import com.example.WebProject.service.ProducerService;

@Transactional
@Repository
public class AccessoryDao {
	@Autowired
	private AccessoryService accessoryService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ColorService colorService;
	@Autowired

	private ProducerService producerService;

	@Autowired

	private MaService maService;

	// xu ly in tien
	public AccessoryInfo findAccessoryInfo(int id) {

		try {
			Accessory gt = accessoryService.findOne(id);
			if (gt == null) {
				return null;
			}

			AccessoryInfo gtinfo = new AccessoryInfo(gt.getId(), gt.getName(), gt.getCategoryid().getCategory(),
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
	public AccessoryInfo findAccessoryInfoSave(int id) {

		try {
			Accessory gt = accessoryService.findOne(id);
			if (gt == null) {
				return null;
			}
			AccessoryInfo gtinfo = new AccessoryInfo(gt.getId(), gt.getName(), gt.getCategoryid().getCategory(),
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
	public List<AccessoryInfo> findAllAccessoryInfo() {

		try {
			List<AccessoryInfo> listaccessory = new ArrayList<AccessoryInfo>();
			List<Accessory> accessory = (List<Accessory>) accessoryService.findAll();

			Accessory gt = new Accessory();
			AccessoryInfo gtinfo;

			for (int i = 0; i < accessory.size(); i++) {
				gt = accessory.get(i);
				gtinfo = new AccessoryInfo(gt.getId(), gt.getName(), gt.getCategoryid().getCategory(),
						gt.getProducerid().getName(), gt.getColorid().getName(), (int) gt.getRate(), gt.getSoluong(),
						GuitarDao.intien(gt.getGia()), gt.getSoluot(), gt.getGiamgia(),
						GuitarDao.intien(gt.getGiasaugiam()));
				if (gt.getDatepr() != null) {
					gtinfo.setDatepr(gt.getDatepr().toString());
				}

				listaccessory.add(gtinfo);

			}

			return listaccessory;
		} catch (NoResultException e) {
			return null;
		}
	}

	// find for index.html
	public List<AccessoryInfo> listAccessoryInfo() {

		try {
			List<AccessoryInfo> listaccessory = new ArrayList<AccessoryInfo>();
			List<Accessory> accessory = (List<Accessory>) accessoryService.findAll();

			Accessory gt = new Accessory();
			AccessoryInfo gtinfo;

			for (int i = accessory.size() - 1; i >= accessory.size() - 4; i--) {

				if (i < 0) {
					return listaccessory;
				}
				gt = accessory.get(i);
				if (gt == null) {
					return listaccessory;
				}
				gtinfo = new AccessoryInfo(gt.getId(), gt.getName(), gt.getCategoryid().getCategory(),
						gt.getProducerid().getName(), gt.getColorid().getName(), (int) gt.getRate(), gt.getSoluong(),
						GuitarDao.intien(gt.getGia()), gt.getSoluot(), gt.getGiamgia(),
						GuitarDao.intien(gt.getGiasaugiam()));
				if (gt.getDatepr() != null) {
					gtinfo.setDatepr(gt.getDatepr().toString());
				}

				listaccessory.add(gtinfo);

			}

			return listaccessory;
		} catch (NoResultException e) {
			return null;
		}
	}

	public void save(AccessoryInfo accessoryInfo) {
		// create object Accessory from accessoryinfo
		final byte[] accessoryImage = accessoryService.findOne(accessoryInfo.getId()).getImage();
		Accessory gt = new Accessory(accessoryInfo.getId(), accessoryInfo.getName(),
				categoryService.findByCategoryContaining(accessoryInfo.getCategory()).get(0),
				producerService.findByNameContaining(accessoryInfo.getProducer()).get(0),
				colorService.findByNameContaining(accessoryInfo.getColor()).get(0), accessoryInfo.getSoluong(),
				accessoryInfo.getGia(), accessoryInfo.getGiamgia(),
				GuitarDao.GiaSauGiam(Integer.parseInt(accessoryInfo.getGia()), accessoryInfo.getGiamgia()));
		// set date
		if (accessoryInfo.getDatepr().toString().trim().equals("") == false) {

			String date = accessoryInfo.getDatepr().toString();
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

		if (accessoryInfo.getFileData().isEmpty() == false) {
			byte[] image = null;
			try {
				image = accessoryInfo.getFileData().getBytes();
			} catch (IOException e) {
			}
			if (image != null && image.length > 0) {
				gt.setImage(image);
			}
		} else
			gt.setImage(accessoryImage);

		accessoryService.save(gt);

	}

	public void SaveCreate(AccessoryInfo accessoryInfo) {
		Ma ma = maService.findOne(1);
		// int entity Accessory from accessoryInfo-non gianiemyet, rate, status
		Accessory gt = new Accessory(ma.getAccessory(), accessoryInfo.getName(),
				categoryService.findByCategoryContaining(accessoryInfo.getCategory()).get(0),
				producerService.findByNameContaining(accessoryInfo.getProducer()).get(0),
				colorService.findByNameContaining(accessoryInfo.getColor()).get(0), 0, 0, accessoryInfo.getSoluong(),
				accessoryInfo.getGia(), accessoryInfo.getGiamgia(),
				GuitarDao.GiaSauGiam(Integer.parseInt(accessoryInfo.getGia()), accessoryInfo.getGiamgia()), 0);
		// set date
		if (accessoryInfo.getDatepr().toString().trim().equals("") == false) {

			String date = accessoryInfo.getDatepr().toString();
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
		if (accessoryInfo.getFileData() != null) {
			byte[] image = null;
			try {
				image = accessoryInfo.getFileData().getBytes();
			} catch (IOException e) {
			}
			if (image != null && image.length > 0) {
				gt.setImage(image);
			}
		}

		accessoryService.save(gt);

		Ma vma=new Ma(1,ma.getGuitar(), ma.getPiano(), ma.getUkulele(),
  				ma.getDrum(), ma.getFlute(), ma.getAccessory()+1, ma.getProducer(),
  				ma.getColor() );		
  		maService.save(vma);

	}

}
