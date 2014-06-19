package com.nixgon.steak;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.nixgon.steak.model.SteakDishModel;
import com.nixgon.steak.model.SteakModel;
import com.nixgon.steak.model.SteakShapeModel;
import com.nixgon.steak.model.SteakTableModel;

@Controller
public class SteakForkController {
	private static final Logger log = Logger.getLogger( SteakForkController.class.getName() );

	private SteakTableModel steakTable = null;
	private List< SteakTableModel > steakTables = null;
	private List< SteakShapeModel > shapes = null;
	private ArrayList< SteakDishModel > steakDish = new ArrayList< SteakDishModel >();
	private ArrayList< SteakModel > steaks = new ArrayList< SteakModel >();
	private String owner = null;

	@RequestMapping(value = "/insertDish", method = RequestMethod.POST)
	public ModelAndView insertDish( HttpServletRequest request ) {
		String dishName = request.getParameter( "dishName" );

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			ArrayList< String > dishList = steakTable.getDish();
			dishList.add( dishName );
			ArrayList< String > columnList = steakTable.getColumns();
			SteakTableModel table = new SteakTableModel( steakTable.getTable(), owner, dishList, columnList,
					steakTable.getCreatedDate(), new Date() );
			SteakDishModel dish = new SteakDishModel( dishName, owner, steakTable.getTable(), new Date(), new Date() );

			pm.makePersistent( table );
			pm.makePersistent( dish );

			log.info( "Inset dish." );
		} finally {
			pm.close();
		}

		ModelAndView mav = new ModelAndView( "steak" );
		mav.setViewName( "data" );
		mav.addObject( "steakTable", steakTable );
		mav.addObject( "steakTables", steakTables );
		mav.addObject( "steakDish", steakDish );
		mav.addObject( "steaks", steaks );
		mav.addObject( "shapes", shapes );
		mav.addObject( "owner", owner );

		return mav;
	}

	@RequestMapping(value = "/login")
	public String loginUser() {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		if ( user != null ) {
			return "redirect:steak";
		} else {
			return "redirect:" + userService.createLoginURL( "../steak" );
		}
	}

	@RequestMapping(value = "/steak")
	public String steak( ModelMap model ) {
		steakTable = null;
		steakDish = new ArrayList< SteakDishModel >();
		steaks = new ArrayList< SteakModel >();
		owner = null;
		List< SteakDishModel > dishResults = null;
		List< SteakModel > steakResults = null;
		SteakShapeModel shape = null;

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		owner = user.getEmail().replaceAll( "@", "at" );
		owner = owner.replaceAll( "\\.", "dot" );

		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query qTable = pm.newQuery( SteakTableModel.class );
		qTable.setFilter( "owner == '" + owner + "'" );

		try {
			steakTables = (List< SteakTableModel >) qTable.execute();

			log.info( "table size : " + steakTables.size() );

			if ( steakTables.isEmpty() ) {
				// 테이블이 하나도 없을 때, 새로운 테이블을 생성, 테이블 크기 정보도 초기값으로 설정
				steakTable = new SteakTableModel( "Empty table", owner, new Date(), new Date() );
				steakTables = new ArrayList< SteakTableModel >();
				steakTables.add( steakTable );

				shape = new SteakShapeModel( owner + "_" + steakTable.getTable() );

				pm.makePersistent( steakTable );
				pm.makePersistent( shape );
			} else {
				steakTable = steakTables.get( 0 );
			}
		} finally {
			qTable.closeAll();
			pm.close();
		}

		pm = PMF.get().getPersistenceManager();

		try {
			Query qDish = pm.newQuery( SteakDishModel.class );
			qDish.setFilter( "table == '" + steakTable.getTable() + "'" );
			Query qSteak = pm.newQuery( SteakModel.class );
			qSteak.setFilter( "table == '" + steakTable.getTable() + "'" );
			Query qShape = pm.newQuery( SteakShapeModel.class );
			qShape.setFilter( "ownerTable == '" + owner + "_" + steakTable.getTable() + "'" );

			try {
				dishResults = (List< SteakDishModel >) qDish.execute();
				steakResults = (List< SteakModel >) qSteak.execute();
				shapes = (List< SteakShapeModel >) qShape.execute();

				log.info( "dish size : " + dishResults.size() );
				log.info( "steak size : " + steakResults.size() );

				if ( dishResults.isEmpty() ) {
					// 테이블에 해당하는 디쉬가 하나도 없을 때, 새로운 디쉬 생성
					steakDish.add( new SteakDishModel( "Main dish", owner, steakTable.getTable(), new Date(),
							new Date() ) );
					pm.makePersistent( steakDish.get( 0 ) );
				} else {
					// 디쉬가 있으면 디쉬 별로 스테이크들을 정리
					for ( String dishName : steakTable.getDish() ) {
						for ( SteakDishModel dish : dishResults ) {
							if ( dish.getDish().equals( dishName ) ) {
								steakDish.add( dish );
								break;
							}
						}

						for ( SteakModel steak : steakResults ) {
							if ( steak.getDish().equals( dishName ) ) {
								steaks.add( steak );
							}
						}
					}
				}

				if ( shapes.isEmpty() ) {
					shape = new SteakShapeModel( owner + "_" + "Empty table" );
				} else {
					shape = shapes.get( 0 );
				}
			} finally {
				qDish.closeAll();
				qSteak.closeAll();
				pm.close();
			}
		} catch ( NullPointerException e ) {
			log.warning( "null : get dish and steak" );
		}

		model.addAttribute( "steakTable", steakTable );
		model.addAttribute( "steakTables", steakTables );
		model.addAttribute( "steakDish", steakDish );
		model.addAttribute( "steaks", steaks );
		model.addAttribute( "shape", shape );
		model.addAttribute( "shapes", shapes );
		model.addAttribute( "owner", owner );

		return "steak";
	}
}
