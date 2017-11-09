package com.ravi.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ravi.dao.ExampleDAO;
import com.ravi.model.DAMProfile;
import com.ravi.model.ExcelModel;
import com.ravi.util.CsvParser;

@Controller
public class DashboardController {
	
	@Autowired
	private CsvParser csvParser;
	
	@Autowired
	private ExampleDAO dao;
	
	@RequestMapping("/damProfiles")
	@ResponseBody
	public List<DAMProfile> getDamFiles() {
		List<DAMProfile> list = new ArrayList<DAMProfile>();
		DAMProfile dam = new DAMProfile();
		dam.setDamName("damfile1");
		dam.setDamId(1);
		dam.setDamVersion("1");
		list.add(dam);
		DAMProfile dam2 = new DAMProfile();
		dam2.setDamName("damfile1");
		dam2.setDamId(2);
		dam2.setDamVersion("2");
		list.add(dam2);
		return list;
	}
	
	@PostMapping("/csvupload")
    public @ResponseBody int[] uploadFileMulti(Principal user, @RequestParam("uploadfile") MultipartFile file) {
    	try {
    		System.out.println("prinitng filename"+ file.getOriginalFilename());
    		List<ExcelModel> list= csvParser.openCSVParser(file.getInputStream());
    		list.size();
    		int [] insertSize = dao.batchUpdate(list, file.getOriginalFilename(), user.getName());
			return insertSize;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			//throw new Exception("FAIL! Maybe You had uploaded the file before or the file's size > 500KB");
		}
    }

}
