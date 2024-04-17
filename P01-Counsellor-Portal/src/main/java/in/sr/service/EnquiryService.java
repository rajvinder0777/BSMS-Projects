package in.sr.service;

import java.util.List;

import in.sr.dto.Dashboard;
import in.sr.entity.Enquiry;

public interface EnquiryService {
   Dashboard getdashboardInfo(Integer cid);

   boolean addEnquiry(Enquiry enq, Integer cid);

   List<Enquiry> getEnquiries(Enquiry e, Integer cid);

   Enquiry getEnquiry(Integer eid);
}
