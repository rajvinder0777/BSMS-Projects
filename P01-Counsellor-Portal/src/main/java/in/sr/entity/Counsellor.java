package in.sr.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Counsellor {
   @Id
   @GeneratedValue(
      strategy = GenerationType.IDENTITY
   )
   private Integer cid;
   private String name;
   private String email;
   private String pwd;
   private Long phno;
   @CreationTimestamp
   private LocalDate cdate;
   @UpdateTimestamp
   private LocalDate udate;
   @OneToMany(
      mappedBy = "cid",
      cascade = {CascadeType.ALL}
   )
   private List<Enquiry> enquiries;

   public Integer getCid() {
      return this.cid;
   }

   public void setCid(Integer cid) {
      this.cid = cid;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getEmail() {
      return this.email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getPwd() {
      return this.pwd;
   }

   public void setPwd(String pwd) {
      this.pwd = pwd;
   }

   public Long getPhno() {
      return this.phno;
   }

   public void setPhno(Long phno) {
      this.phno = phno;
   }

   public LocalDate getCdate() {
      return this.cdate;
   }

   public void setCdate(LocalDate cdate) {
      this.cdate = cdate;
   }

   public LocalDate getUdate() {
      return this.udate;
   }

   public void setUdate(LocalDate udate) {
      this.udate = udate;
   }

   public List<Enquiry> getEnquiries() {
      return this.enquiries;
   }

   public void setEnquiries(List<Enquiry> enquiries) {
      this.enquiries = enquiries;
   }
}
    

	

