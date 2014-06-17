package com.nixgon.steak;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

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
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		String owner = user.getEmail().replaceAll( "@", "at" );
		owner = owner.replaceAll( "\\.", "dot" );

		SteakTableModel steakTable = null;
		List< SteakTableModel > steakTables = null;
		List< SteakDishModel > steakDish = null;
		List< SteakModel > steaks = null;
		List< SteakShapeModel > shapes = null;
		SteakShapeModel shape = null;

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

				shape = new SteakShapeModel( steakTable.getTable() );

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
			qShape.setFilter( "table == '" + steakTable.getTable() + "'" );

			try {
				steakDish = (List< SteakDishModel >) qDish.execute();
				steaks = (List< SteakModel >) qSteak.execute();
				shapes = (List< SteakShapeModel >) qShape.execute();

				log.info( "dish size : " + steakDish.size() );
				log.info( "steak size : " + steaks.size() );

				if ( steakDish.isEmpty() ) {
					// 테이블에 해당하는 디쉬가 하나도 없을 때, 새로운 디쉬 생성
					steakDish = new ArrayList< SteakDishModel >();
					steakDish.add( new SteakDishModel( "Main dish", owner, steakTable.getTable(), new Date(),
							new Date() ) );
					pm.makePersistent( steakDish.get( 0 ) );
				}

				if ( shapes.isEmpty() == false ) {
					shape = shapes.get( 0 );
				}
			} finally {
				qDish.closeAll();
				qSteak.closeAll();
				pm.close();
			}
		} catch ( NullPointerException e ) {
			log.info( "null : get dish and steak" );
		}

		model.addAttribute( "steakTable", steakTable );
		model.addAttribute( "steakTables", steakTables );
		model.addAttribute( "steakDish", steakDish );
		model.addAttribute( "steaks", steaks );
		model.addAttribute( "shape", shape );
		model.addAttribute( "owner", owner );

		return "steak";
	}
}
