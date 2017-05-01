package kokofarm.product.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kokofarm.product.domain.PostDTO;
import kokofarm.product.service.ProductService;

public class InsertPostAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProductService service = ProductService.getInstance();
		PostDTO
		post = new PostDTO();
		String product_no = request.getParameter("product_no");
		
		post.setProduct_no(product_no);
		post.setPost_content(request.getParameter("post_content"));
		
		service.InsertPostService(post);
		
		System.out.println("id"+post.getMember_id());
		System.out.println("con"+post.getPost_content());
		System.out.println("no"+post.getProduct_no());
		System.out.println("date"+post.getPost_date());
		
		ActionForward forward = new ActionForward();
		forward.setPath("detailProdutAction.product");
		forward.setRedirect(false);
		
		return forward;
	}

}
