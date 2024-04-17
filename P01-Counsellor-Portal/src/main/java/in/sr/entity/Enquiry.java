package in.sr.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Enquiry {
   @Id
   @GeneratedValue(
      strategy = GenerationType.IDENTITY
   )
   private Integer eid;
   private String sname;
   private Long sphno;
   private String classmode;
   private String course;
   private String status;
   @CreationTimestamp
   @Column(
      updatable = false
   )
   private LocalDate cdate;
   @UpdateTimestamp
   @Column(
      insertable = false
   )
   private LocalDate udate;
   @ManyToOne
   @JoinColumn(
      name = "cid"
   )
   private Counsellor cid;

   public Integer getEid() {
      return this.eid;
   }

   public void setEid(Integer eid) {
      this.eid = eid;
   }

   public String getSname() {
      return this.sname;
   }

   public void setSname(String sname) {
      this.sname = sname;
   }

   public Long getSphno() {
      return this.sphno;
   }

   public void setSphno(Long sphno) {
      this.sphno = sphno;
   }

   public String getClassmode() {
      return this.classmode;
   }

   public void setClassmode(String classmode) {
      this.classmode = classmode;
   }

   public String getCourse() {
      return this.course;
   }

   public void setCourse(String course) {
      this.course = course;
   }

   public String getStatus() {
      return this.status;
   }

   public void setStatus(String status) {
      this.status = status;
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

   public Counsellor getCid() {
      return this.cid;
   }

   public void setCid(Counsellor cid) {
      this.cid = cid;
   }
}
