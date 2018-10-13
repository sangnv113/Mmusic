
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

import com.example.WebProject.entity.Piano;
import com.example.WebProject.entity.Ma;
import com.example.WebProject.model.PianoInfo;
import com.example.WebProject.service.CategoryService;
import com.example.WebProject.service.ColorService;
import com.example.WebProject.service.PianoService;
import com.example.WebProject.service.MaService;
import com.example.WebProject.service.ProducerService;

@Transactional
@Repository
public class PianoDao {
	@Autowired
	private PianoService pianoService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ColorService colorService;
	@Autowired

	private ProducerService producerService;

	@Autowired

	private MaService maService;

	// xu ly in tien
	public PianoInfo findPianoInfo(int id) {

		try {
			Piano gt = pianoService.findOne(id);
			if (gt == null) {
				return null;
			}

			PianoInfo gtinfo = new PianoInfo(gt.getId(), gt.getName(), gt.getCategoryid().getCategory(),
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
	public PianoInfo findPianoInfoSave(int id) {

		try {
			Piano gt = pianoService.findOne(id);
			if (gt == null) {
				return null;
			}
			PianoInfo gtinfo = new PianoInfo(gt.getId(), gt.getName(), gt.getCategoryid().getCategory(),
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
	public List<PianoInfo> findAllPianoInfo() {

		try {
			List<PianoInfo> listpiano = new ArrayList<PianoInfo>();
			List<Piano> piano = (List<Piano>) pianoService.findAll();

			Piano gt = new Piano();
			PianoInfo gtinfo;

			for (int i = 0; i < piano.size(); i++) {
				gt = piano.get(i);
				gtinfo = new PianoInfo(gt.getId(), gt.getName(), gt.getCategoryid().getCategory(),
						gt.getProducerid().getName(), gt.getColorid().getName(), (int) gt.getRate(), gt.getSoluong(),
						GuitarDao.intien(gt.getGia()), gt.getSoluot(), gt.getGiamgia(),
						GuitarDao.intien(gt.getGiasaugiam()));
				if (gt.getDatepr() != null) {
					gtinfo.setDatepr(gt.getDatepr().toString());
				}

				listpiano.add(gtinfo);

			}

			return listpiano;
		} catch (NoResultException e) {
			return null;
		}
	}

	// find for index.html
	public List<PianoInfo> listPianoInfo() {

		try {
			List<PianoInfo> listpiano = new ArrayList<PianoInfo>();
			List<Piano> piano = (List<Piano>) pianoService.findAll();

			Piano gt = new Piano();
			PianoInfo gtinfo;

			for (int i = piano.size() - 1; i >= piano.size() - 4; i--) {

				if (i < 0) {
					return listpiano;
				}
				gt = piano.get(i);
				if (gt == null) {
					return listpiano;
				}
				gtinfo = new PianoInfo(gt.getId(), gt.getName(), gt.getCategoryid().getCategory(),
						gt.getProducerid().getName(), gt.getColorid().getName(), (int) gt.getRate(), gt.getSoluong(),
						GuitarDao.intien(gt.getGia()), gt.getSoluot(), gt.getGiamgia(),
						GuitarDao.intien(gt.getGiasaugiam()));
				if (gt.getDatepr() != null) {
					gtinfo.setDatepr(gt.getDatepr().toString());
				}

				listpiano.add(gtinfo);

			}

			return listpiano;
		} catch (NoResultException e) {
			return null;
		}
	}

	public void save(PianoInfo pianoInfo) {
		// create object Piano from pianoinfo
		final byte[] pianoImage = pianoService.findOne(pianoInfo.getId()).getImage();
		Piano gt = new Piano(pianoInfo.getId(), pianoInfo.getName(),
				categoryService.findByCategoryContaining(pianoInfo.getCategory()).get(0),
				producerService.findByNameContaining(pianoInfo.getProducer()).get(0),
				colorService.findByNameContaining(pianoInfo.getColor()).get(0), pianoInfo.getSoluong(),
				pianoInfo.getGia(), pianoInfo.getGiamgia(),
				GuitarDao.GiaSauGiam(Integer.parseInt(pianoInfo.getGia()), pianoInfo.getGiamgia()));
		// set date
		if (pianoInfo.getDatepr().toString().trim().equals("") == false) {

			String date = pianoInfo.getDatepr().toString();
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

		if (pianoInfo.getFileData().isEmpty() == false) {
			byte[] image = null;
			try {
				image = pianoInfo.getFileData().getBytes();
			} catch (IOException e) {
			}
			if (image != null && image.length > 0) {
				gt.setImage(image);
			}
		} else
			gt.setImage(pianoImage);

		pianoService.save(gt);

	}

	public void SaveCreate(PianoInfo pianoInfo) {
		Ma ma = maService.findOne(1);
		// int entity Piano from pianoInfo-non gianiemyet, rate, status
		Piano gt = new Piano(ma.getPiano(), pianoInfo.getName(),
				categoryService.findByCategoryContaining(pianoInfo.getCategory()).get(0),
				producerService.findByNameContaining(pianoInfo.getProducer()).get(0),
				colorService.findByNameContaining(pianoInfo.getColor()).get(0), 0, 0, pianoInfo.getSoluong(),
				pianoInfo.getGia(), pianoInfo.getGiamgia(),
				GuitarDao.GiaSauGiam(Integer.parseInt(pianoInfo.getGia()), pianoInfo.getGiamgia()), 0);
		// set date
		if (pianoInfo.getDatepr().toString().trim().equals("") == false) {

			String date = pianoInfo.getDatepr().toString();
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
		if (pianoInfo.getFileData() != null) {
			byte[] image = null;
			try {
				image = pianoInfo.getFileData().getBytes();
			} catch (IOException e) {
			}
			if (image != null && image.length > 0) {
				gt.setImage(image);
			}
		}

		pianoService.save(gt);

  		Ma vma=new Ma(1,ma.getGuitar(), ma.getPiano()+1, ma.getUkulele(),
  				ma.getDrum(), ma.getFlute(), ma.getAccessory(), ma.getProducer(),
  				ma.getColor() );	
  		maService.save(vma);

	}

}
