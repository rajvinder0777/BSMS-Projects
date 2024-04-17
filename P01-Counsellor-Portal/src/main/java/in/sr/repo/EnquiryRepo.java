package in.sr.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.sr.entity.Enquiry;

public interface EnquiryRepo extends JpaRepository<Enquiry, Integer> {
	@Query(value = "select count(*) from enquiry where cid=:cid", nativeQuery = true)
	Long getEnquiries(Integer cid);

	@Query(value = "select count(*) from enquiry where cid=:cid and status=:status", nativeQuery = true)
	Long getEnquiry(Integer cid, String status);
}