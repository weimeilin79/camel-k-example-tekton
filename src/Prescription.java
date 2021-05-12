// camel-k: language=java open-api=prescription.yaml

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Exchange;


public class Prescription extends RouteBuilder {
  
  String mockresult = "{ \"profileid\": 123456, \"patient\": \"Me\", \"complaint\": \"Cold Symptoms\", \"diagnosis\": \"Acute Bronchitis\", \"prescription\": [{ \"medicine\": \"benzonatate\",  \"dosage\": \"3 times per day\" }, { \"medicine\": \"ProAir HFA\", \"dosage\": \"every 4 to 6 hours\" }], \"pharmancy\": \"CVS\" }";
  String statusError404 = "{ \"status\": \"NOT FOUND\"}";
  String statusError201 = "{ \"status\": \"CREATED!\"}";

  @Override
  public void configure() throws Exception {

      from("direct:getProfile")
      .log("START GET")
      .setBody().simple(mockresult)
      .choice()
        .when(simple("${header.profileid} != 123456"))
          .setBody().simple(statusError404)
          .setHeader(Exchange.HTTP_RESPONSE_CODE,constant(404))
      .end()
      .to("log:info")
      ;

      from("direct:putProfile")
        .log("START PUT")
        .log("headers--> ${headers}")
        .to("log:info")  
        .setBody().simple(statusError201)
        .setHeader(Exchange.HTTP_RESPONSE_CODE,constant(201))
      ;
      
  }

/**
{
    "profileid": 123456,
    "patient": "Me",
    "complaint": "Cold Symptoms",
    "diagnosis": "Acute Bronchitis",
    "prescription": [
        {
            "medicine": "benzonatate",
            "dosage": "3 times per day"
        },
        {
            "medicine": "ProAir HFA",
            "dosage": "every 4 to 6 hours"
        }
    ],
    "pharmancy": "CVS"
}
 */
  public static class Profile {

    Integer profileid;
    String patient;
    String complaint;
    String diagnosis;
    String pharmancy;
    Meds prescription;

    public Integer getProfileid() {
      return this.profileid;
    }

    public void setProfileid(Integer profileid) {
      this.profileid = profileid;
    }

    public String getPatient() {
      return this.patient;
    }

    public void setPatient(String patient) {
      this.patient = patient;
    }

    public String getComplaint() {
      return this.complaint;
    }

    public void setComplaint(String complaint) {
      this.complaint = complaint;
    }

    public String getDiagnosis() {
      return this.diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
      this.diagnosis = diagnosis;
    }

    public String getPharmancy() {
      return this.pharmancy;
    }

    public void setPharmancy(String pharmancy) {
      this.pharmancy = pharmancy;
    }

    public Meds getPrescription() {
      return this.prescription;
    }

    public void setPrescription(Meds prescription) {
      this.prescription = prescription;
    }
  }

  public static class Meds {

    String medicine;
    String dosage;

    public String getMedicine() {
      return this.medicine;
    }

    public void setMedicine(String medicine) {
      this.medicine = medicine;
    }

    public String getDosage() {
      return this.dosage;
    }

    public void setDosage(String dosage) {
      this.dosage = dosage;
    }

  }
}
