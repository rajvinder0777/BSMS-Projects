package in.sr.service;

import in.sr.entity.Counsellor;

public interface CounsellorService {
	   boolean saveCounsellor(Counsellor con);

	   Counsellor getCounsellor(String email, String pwd);
	}