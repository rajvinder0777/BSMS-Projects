package in.sr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sr.entity.Counsellor;
import in.sr.repo.CounsellorRepo;

@Service
public class CounsellorServiceImp implements CounsellorService {
	@Autowired
	private CounsellorRepo crepo;

	public boolean saveCounsellor(Counsellor con) {
		Counsellor bym = this.crepo.findByEmail(con.getEmail());
		if (bym != null) {
			return false;
		} else {
			Counsellor sv = (Counsellor) this.crepo.save(con);
			return sv.getCid() != null;
		}
	}

	public Counsellor getCounsellor(String email, String pwd) {
		return this.crepo.findByEmailAndPwd(email, pwd);
	}
}
