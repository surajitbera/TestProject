

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@WebServlet(name = "EmpType", urlPatterns = { "/EmpType" })

public class EmpTypeServlet extends HttpServlet {
private static final long serialVersionUID = 1L;
   	
	static Logger logger = Logger.getLogger(EmpTypeServlet.class);
    public EmpTypeServlet() {
        super();
       
    }

protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
	}

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		        	
 	checkingMethod(request, response);
       
       
   }
   
   public void checkingMethod(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException
	{ 
	    String action_name=request.getParameter("ActionName");		 
		
      

	switch(action_name)
	{
	
	case "add": 
		
		 String emp_type_id=getNewID();
		 String emp_type_name=request.getParameter("emp_type_name");
			
			
		String errorMsg = null;
		if(emp_type_name == null || emp_type_name.equals("")){
			errorMsg = "EmpType Name can't be null or empty.";
		}
		
		if(checkingName(emp_type_name)==true){
			errorMsg = "This EmpType Name alrady Added.";
		}
		
		
		
		if(errorMsg != null){
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/EmpType.jsp");
			PrintWriter out= response.getWriter();
			out.println("<font color=red>"+errorMsg+"</font>");
			rd.include(request, response);
		}else{
		
		
			if (addEmpType(emp_type_id,emp_type_name)==true)
			{
				logger.info(emp_type_name +" ,EmpType is Added Successfuly.");
				
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/EmpType.jsp");
				PrintWriter out= response.getWriter();
				out.println("<font color=green>EmpType is Added Successfuly.</font>");
				rd.include(request, response);
			}
				
		}
		break;
		
	case "edit":
		
	 String edit_EmpTypeID=request.getParameter("emp_type_id");
	 String edit_emp_type_name=request.getParameter("emp_type_name");
	  
	
	String edit_errorMsg = null;
	if(edit_emp_type_name == null || edit_emp_type_name.equals("")){
		edit_errorMsg = "EmpType Name can't be null or empty.";
	}
	
	if(checkingName(edit_emp_type_name)==true){
		edit_errorMsg = "This EmpType Name alrady Added.";
	}
	
	
	
	if(edit_errorMsg != null){
		RequestDispatcher edit_rd = getServletContext().getRequestDispatcher("/EmpType.jsp");
		PrintWriter out1= response.getWriter();
		out1.println("<font color=red>"+edit_errorMsg+"</font>");
		edit_rd.include(request, response);
	}else{
	
	 if (editEmpType(edit_EmpTypeID,edit_emp_type_name)==true)
	 {
		 logger.info(edit_emp_type_name +" ,EmpType is edit successfuly.");
			
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/EmpType.jsp");
			PrintWriter out= response.getWriter();
			out.println("<font color=green>EmpType is Edit Successfuly.</font>");
			rd.include(request, response);
	 }
			
	
	}
	break;
	
	case "remove":
		
		 String remove_emp_type_id=request.getParameter("emp_type_id");
				
		String remove_errorMsg = null;
		if(remove_emp_type_id == null || remove_emp_type_id.equals("")){
			remove_errorMsg = "EmpType ID can't be null or empty.";
		}
		
		if(checkingID(remove_emp_type_id)==true){
			remove_errorMsg = "This EmpType ID is not Found.";
		}
		
			
		if(remove_errorMsg != null){
			RequestDispatcher remove_rd = getServletContext().getRequestDispatcher("/EmpType.jsp");
			PrintWriter out1= response.getWriter();
			out1.println("<font color=red>"+remove_errorMsg+"</font>");
			remove_rd.include(request, response);
		}else{
		
		if (removeEmpType(remove_emp_type_id)==true)
		{
			logger.info("EmpType is remove successfuly.");
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/EmpType.jsp");
			PrintWriter out= response.getWriter();
			out.println("<font color=green>EmpType is Remove Successfuly.</font>");
			rd.include(request, response);
		}
		}
		break;
		
		
	case "search":
		
		String search_emp_type_name=request.getParameter("emp_type_name");
				
		String search_errorMsg = null;
		if(search_emp_type_name == null || search_emp_type_name.equals("")){
			search_errorMsg = "EmpType Name can't be null or empty.";
		}
		
				
		
		if(search_errorMsg != null){
			RequestDispatcher search_rd = getServletContext().getRequestDispatcher("/EmpType.jsp");
			PrintWriter out2= response.getWriter();
			out2.println("<font color=red>"+search_errorMsg+"</font>");
			search_rd.include(request, response);
		}else{
		
			if (searchEmpType(search_emp_type_name)==true)
			{
				

				
					//EmpType cut = new EmpType();
					//logger.info("EmpType found with details="+cut);
					//HttpSession session = request.getSession();
					//session.setAttribute("EmpType", cut);
					//response.sendRedirect("home.jsp");
			}
		}
		break;
		
		}
		
	}
   
      private boolean addEmpType(String emp_type_id,String emp_type_name  ) throws ServletException 
      {
    	  Connection con = (Connection) getServletContext().getAttribute("DBConnection");
  		
  		PreparedStatement ps = null;
  		try {
  			ps = con.prepareStatement("insert into tbl_001_employee_type_master(emp_type_id, emp_type_name) values (?,?)");
  			ps.setString(1, emp_type_id);
  			ps.setString(2, emp_type_name);
  						
  			ps.execute();
  			return true;
  		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Database connection problem");
			throw new ServletException("DB Connection problem.");
		}finally{
			try {
				ps.close();
			} catch (SQLException e) {
				logger.error("SQLException in closing PreparedStatement");
			}
		}
	
      
      }
      
      private boolean editEmpType(String emp_type_id,String emp_type_name  ) throws ServletException 
      {
    	  
    	  Connection con = (Connection) getServletContext().getAttribute("DBConnection");
    		
    		PreparedStatement ps = null;
    		try {
    			ps = con.prepareStatement("update tbl_001_employee_type_master set  emp_type_name=? where emp_type_id=? ");
    			ps.setString(1, emp_type_name);
    			ps.setString(2, emp_type_id);
      		  				
    			ps.execute();
      
    			return true;
      		} catch (SQLException e) {
    			e.printStackTrace();
    			logger.error("Database connection problem");
    			throw new ServletException("DB Connection problem.");
    		}finally{
    			try {
    				ps.close();
    			} catch (SQLException e) {
    				logger.error("SQLException in closing PreparedStatement");
    			}
    		}
    	
          
          }
          
      
      private boolean removeEmpType(String emp_type_id) throws ServletException
      {
    	  
    	  Connection con = (Connection) getServletContext().getAttribute("DBConnection");
  		
  		PreparedStatement ps = null;
  		try {
  			ps = con.prepareStatement("delete from tbl_001_employee_type_master where emp_type_id=? ");
  			
  		    ps.setString(1, emp_type_id);
  		    				
  			ps.execute();
  			return true;
  		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Database connection problem");
			throw new ServletException("DB Connection problem.");
		}finally{
			try {
				ps.close();
			} catch (SQLException e) {
				logger.error("SQLException in closing PreparedStatement");
			}
		}
    	  
      }
      
      private boolean searchEmpType(String search_emp_type_name) throws ServletException
      {
    	  Connection con = (Connection) getServletContext().getAttribute("DBConnection");
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement("select emp_type_name " 
				+ " from tbl_001_employee_type_master a where emp_type_name=? ");
								
				ps.setString(1, search_emp_type_name);
				rs = ps.executeQuery();
				
				ResultSetMetaData metadata = rs.getMetaData();
				int numcols = metadata.getColumnCount();
				ArrayList<String> AR_List = new ArrayList<String>();
				while (rs.next()) {
					 int i = 1;
				    while (i <= numcols) {
				    	AR_List.add(rs.getString(i++));
				    }
				}
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("Database connection problem");
				throw new ServletException("DB Connection problem.");
			}finally{
				try {
					ps.close();
				} catch (SQLException e) {
					logger.error("SQLException in closing PreparedStatement");
				}
			}
			
    	  
      }
      
   
   private String getNewID()
	  {
		 String NewID ="" ;
		 int id =0;
			 
		 String sql_str ="SELECT abs(ifnull(max(emp_type_id),0)+1)new_id FROM tbl_001_employee_type_master" ;
		 	 
			try {
				Connection con = (Connection) getServletContext().getAttribute("DBConnection");
							
				PreparedStatement ps=con.prepareStatement(sql_str);
				ResultSet rs =ps.executeQuery();
				
				if(rs.next()){
					
					id=Integer.parseInt(rs.getString(1));
					NumberFormat formatter = new DecimalFormat("000");
					NewID=formatter.format(id);	
					return NewID;		
				}
								
				else
				{
					return "";
				}
									
			} catch (Exception e) {
						
				System.out.println(e.getMessage());
				return "";
			}
		 
		     	
	    }
   
   private boolean checkingID(String checkValue)
   {
   	      	 
   	Connection con = (Connection) getServletContext().getAttribute("DBConnection");
 		PreparedStatement ps = null;
 		ResultSet rs = null;
 		try {
 			ps = con.prepareStatement("select emp_type_id from tbl_001_employee_type_master where emp_type_id=? limit 1");
 			ps.setString(1, checkValue);
 		    rs = ps.executeQuery();
 				    
 			if(rs.next()){
 						
 				String name= rs.getString(1);
 						
 				if (name!="" && name!=null)
 				{
 					return true;			
 				}
 				else
 				{
 					return false;
 				}
 			   
 			}
 			else
 			{
 				return false;
 			}
 						
 			    								
   		} catch (Exception e) {
   					
   			System.out.println("error"+e.getMessage());
   			logger.error("Chacking Name Operation Error : "+ e.getMessage());
   			return false;
   		}
 		
   }
 
 private boolean checkingName(String checkValue)
 {
 	      	 
 	Connection con = (Connection) getServletContext().getAttribute("DBConnection");
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("select emp_type_name from tbl_001_employee_type_master where emp_type_name=?  limit 1");
			ps.setString(1, checkValue);
			
		    rs = ps.executeQuery();
				    
			if(rs.next()){
						
				String name= rs.getString(1);
						
				if (name!="" && name!=null)
				{
					return true;			
				}
				else
				{
					return false;
				}
			   
			}
			else
			{
				return false;
			}
						
			    								
 		} catch (Exception e) {
 					
 			System.out.println("error"+e.getMessage());
 			logger.error("Chacking Name Operation Error : "+ e.getMessage());
 			return false;
 		}
		
 }
 

}
	


