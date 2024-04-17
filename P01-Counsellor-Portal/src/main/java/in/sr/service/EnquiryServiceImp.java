package in.sr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import in.sr.dto.Dashboard;
import in.sr.entity.Counsellor;
import in.sr.entity.Enquiry;
import in.sr.repo.CounsellorRepo;
import in.sr.repo.EnquiryRepo;

@Service
public class EnquiryServiceImp implements EnquiryService {
	@Autowired
	private EnquiryRepo erepo;
	@Autowired
	private CounsellorRepo crepo;

	public Dashboard getdashboardInfo(Integer cid) {
		Long tenq = this.erepo.getEnquiries(cid);
		Long oenq = this.erepo.getEnquiry(cid, "New");
		Long lenq = this.erepo.getEnquiry(cid, "Lost");
		Long eenq = this.erepo.getEnquiry(cid, "Enrolled");
		Dashboard d = new Dashboard();
		d.settEnq(tenq);
		d.setoEnq(oenq);
		d.setlEnq(lenq);
		d.seteEnq(eenq);
		return d;
	}

	public boolean addEnquiry(Enquiry enq, Integer cid) {
		Counsellor c = (Counsellor) this.crepo.findById(cid).orElseThrow();
		enq.setCid(c);
		Enquiry save = (Enquiry) this.erepo.save(enq);
		return save != null;
	}

	public List<Enquiry> getEnquiries(Enquiry e, Integer cid) {
		Counsellor cr = new Counsellor();
		cr.setCid(cid);
		Enquiry searchenq = new Enquiry();
		searchenq.setCid(cr);
		if (e.getClassmode() != null && !"".equals(e.getClassmode())) {
			searchenq.setClassmode(e.getClassmode());
		}

		if (e.getCourse() != null && !"".equals(e.getCourse())) {
			searchenq.setCourse(e.getCourse());
		}

		if (e.getStatus() != null && !"".equals(e.getStatus())) {
			searchenq.setStatus(e.getStatus());
		}

		Example<Enquiry> of = Example.of(searchenq);
		return this.erepo.findAll(of);
	}

	public Enquiry getEnquiry(Integer eid) {
		return (Enquiry) this.erepo.findById(eid).orElseThrow();
	}
}