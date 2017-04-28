package kokofarm.product.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.tribes.group.interceptors.FragmentationInterceptor;

import kokofarm.product.action.Action;
import kokofarm.product.action.ActionForward;
import kokofarm.product.action.InsertProductAction;
import kokofarm.product.action.ListproductAction;
import kokofarm.product.action.detailProdutAction;


@WebServlet("*.product")
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	public ProductController() {
		super();
	}

	public void doProcess(HttpServletResponse response, HttpServletRequest request)
			throws ServletException, IOException {
		
		String requestURI = request.getRequestURI();
		System.out.println(requestURI);
		int index = requestURI.lastIndexOf("/");
		System.out.println(index);
		
		String path = requestURI.substring(index+1, requestURI.length());
		System.out.println(path);

		ActionForward forward = null;
		Action action = null;

		
		if (path.equals("InsertProductAction.product")) {
			System.out.println("insert!!");
			// insertForm.jsp로 이동하기 위한 목적
			action = new InsertProductAction();

			try {
				forward = action.execute(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(path.equals("listproductAction.product")) {
			System.out.println("list!!");
			// insertForm.jsp로 이동하기 위한 목적
			action = new ListproductAction();

			try {
				forward = action.execute(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(path.indexOf("detailProdutAction.product") != -1){
			System.out.println("detail");
			action = new detailProdutAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(path.equals("PostInsertAction.product")){
			System.out.println("insertpost");
			
			try {
				forward = action.execute(request, response);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		  if (forward != null) {
		         if (forward.isRdirect()) {
		            response.sendRedirect(forward.getPath());
		         } else {
		            RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
		            dispatcher.forward(request, response);
		         }
		      }
		  
		   }
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(response, request);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(response, request);
	}

}
