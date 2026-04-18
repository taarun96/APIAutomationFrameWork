package com.database.dao;

import java.util.ArrayList;
import java.util.List;

import com.api.request.model.CreateJobPayload;
import com.api.utils.CreateJobBeanMapper;
import com.dataproviders.api.bean.CreateJobBean;

public class DaoDemoRunner {

	public static void main(String[] args) {
		// Fetching data from the DAO
		List<CreateJobBean> beanList = CreateJobAPIPayloadDataDao.getCreateJobPayLoadData();
		List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();

		// Mapping Bean objects to Payload objects
		for (CreateJobBean createJobBean : beanList) {
			CreateJobPayload payload = CreateJobBeanMapper.mapper(createJobBean);
			payloadList.add(payload);
		}

		System.out.println("--------------------------------------------------");

		// Printing the processed payloads
		for (CreateJobPayload payload : payloadList) {
			System.out.println(payload);
		}
	}
}
