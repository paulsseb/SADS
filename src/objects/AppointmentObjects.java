package objects;



public class AppointmentObjects {
		
   public static class AppointmentObj{
    	private String appointmentId;
    	private String user_id;
    	private String status;
    	private String date;
    	private String time;
    	private String reason;
    	private String patientName;
    	private String Msg;
    	private String Success;
    	
    	public String getpatientName() {
    		return patientName;
    	}
    	public void setpatientName(String patientName) {
    		this.patientName = patientName;
    	}
    	
    	public String getappointmentId() {
    		return appointmentId;
    	}
    	public void setappointmentId(String appointmentId) {
    		this.appointmentId = appointmentId;
    	}
    	public String getuser_id() {
    		return user_id;
    	}
    	public void setuser_id(String user_id) {
    		this.user_id = user_id;
    	}
    	public String status() {
    		return status;
    	}
    	public void setstatus(String status) {
    		this.status = status;
    	}

    	public String getdate() {
    		return date;
    	}
    	public void setdate(String date) {
    		this.date = date;
    	}
    	public String gettime() {
    		return time;
    	}
    	public void settime(String time) {
    		this.time = time;
    	}
    	public String getreason() {
    		return reason;
    	}
    	public void setreason(String reason) {
    		this.reason = reason;
    	}
    	public String getMsg() {
    		return Msg;
    	}
    	public void setMsg(String msg) {
    		Msg = msg;
    	}
    	public String getSuccess() {
    		return Success;
    	}
    	public void setSuccess(String success) {
    		Success = success;
    	}
   } 
}