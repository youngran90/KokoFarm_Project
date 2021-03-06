package kokofarm.tender.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kokofarm.register.model.RegDto;
import kokofarm.tender.domain.AuctionDto;
import kokofarm.tender.domain.TenderDto;
import kokofarm.tender.persistence.AuctionDao;

public class TenderFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AuctionDao dao= AuctionDao.getInstance();
		
		//
		HttpSession session=request.getSession();
		RegDto auction1=(RegDto)session.getAttribute("regDto");
		//System.out.println("민경이꺼 넘어오나"+auction1);
		
		AuctionDto auction=dao.selectAuctionProduct(auction1.getAuction_no());
		request.setAttribute("auction", auction);
		String seller_name=dao.selectSeller(auction1.getAuction_no());
		request.setAttribute("seller_name", seller_name);
		System.out.println(seller_name);
		int current_price= dao.selectCurrentPrice(auction1.getAuction_no());
		request.setAttribute("current_price", current_price);
		
		String start_time = auction.getStart_date();
		System.out.println("시작시간"+start_time);
		int s_year= Integer.parseInt(start_time.substring(0, 4));
		int s_month= Integer.parseInt(start_time.substring(5, 7));
		int s_day = Integer.parseInt(start_time.substring(8,10));
		int s_hour = Integer.parseInt(start_time.substring(11,13));
		int s_minute = Integer.parseInt(start_time.substring(14,16));
		int s_second = Integer.parseInt(start_time.substring(17,19));
		
		int startTime=s_hour*60*60+s_minute*60+s_second;
		
		String end_time = auction.getEnd_date();
		int e_year = Integer.parseInt(end_time.substring(0,4));
		int e_month=Integer.parseInt(end_time.substring(5,7));
		int e_day=Integer.parseInt(end_time.substring(8,10));
		int e_hour=Integer.parseInt(end_time.substring(11,13));
		int e_minute= Integer.parseInt(end_time.substring(14,16));
		int e_second=Integer.parseInt(end_time.substring(17,19));
		
		int endTime=e_hour*60*60+e_minute*60+e_second;
		/*int year= e_year-s_year;
		int month=e_month- s_month;
		int day= e_day-s_day;
		int hour=e_hour-s_hour;
		int minute = e_minute-s_minute;
		int second= e_second-s_second;
		
		int tending_time=0;
		if(year==0){
			if(month==0){
				if(day==0){
					tending_time=hour*60*60+minute*60+second;
				}
				tending_time=day*24*60*60+hour*60*60+minute*60+second;
			}
			tending_time=day*24*60*60+hour*60*60+minute*60+second;
		}
		System.out.println(tending_time);
		request.setAttribute("tending_time", tending_time);*/
		
		Calendar cal= Calendar.getInstance();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String visitTime=sf.format(cal.getTime());
		System.out.println(visitTime);
		
	
		int v_year = Integer.parseInt(visitTime.substring(0,4));
		int v_month=Integer.parseInt(visitTime.substring(5,7));
		int v_day=Integer.parseInt(visitTime.substring(8,10));
		int v_hour=Integer.parseInt(visitTime.substring(11,13));
		int v_minute= Integer.parseInt(visitTime.substring(14,16));
		int v_second=Integer.parseInt(visitTime.substring(17,19));
		System.out.println(v_year);
		System.out.println(v_month);
		System.out.println(v_day);
		System.out.println(v_hour);
		System.out.println(v_minute);
		System.out.println(v_day);
		
		int currentvisitTime=v_hour*60*60+v_minute*60+v_second;
		
		int year1= e_year-v_year;
		int month1=e_month- v_month;
		int day1= e_day-v_day;
		int hour1=e_hour-v_hour;
		int minute1 = e_minute-v_minute;
		int second1= e_second-v_second;
				
		int visitingTime=0;
		if(currentvisitTime-startTime>0 && endTime-currentvisitTime >0){
			
			if(year1==0){
				if(month1==0){
					if(day1==0){
						visitingTime=hour1*60*60+minute1*60+second1;
					}
					visitingTime=day1*24*60*60+hour1*60*60+minute1*60+second1;
				}
				visitingTime=day1*24*60*60+hour1*60*60+minute1*60+second1;
			}
		}
		System.out.println(visitingTime);
		request.setAttribute("visitingTime", visitingTime);
		
		
		//int tender_price=Integer.parseInt(request.getParameter("tender_price"));
		
		TenderDto tender;
		if(visitingTime==0){  
			if(current_price!=0){ 
				tender= dao.selectSuccess(auction1.getAuction_no(), current_price);
				System.out.println(tender);
				dao.updateTender(current_price);
			}else{  
				dao.updateAuctionResult(auction1.getAuction_no());  
			}
		}else{
			if(auction.getAuction_up()==current_price){
				visitingTime=0;
				request.setAttribute("visitingTime", visitingTime);
				/*tender= dao.selectSuccess("a6", current_price);
				System.out.println(tender);
				dao.updateTender(current_price);*/
			}
		}
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("/kokofarm.tender.view/tenderform.jsp");
		
		return forward;
	}

}
