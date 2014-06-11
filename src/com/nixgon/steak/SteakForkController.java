package com.nixgon.steak;

import java.util.ArrayList;
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
import com.nixgon.steak.model.SteakDataModel;
import com.nixgon.steak.model.SteakPipelineModel;
import com.nixgon.steak.model.SteakStageModel;

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

		ArrayList< String > pipelines = new ArrayList< String >();
		ArrayList< String > stages = new ArrayList< String >();
		ArrayList< String > columns = new ArrayList< String >();

		// Calendar c = Calendar.getInstance();
		// c.add( Calendar.DATE, 7 );
		// Date dueDate = c.getTime();
		//
		// ArrayList< SteakDataModel > steaks1 = new ArrayList< SteakDataModel
		// >();
		// for ( int i = 0; i < 4; i++ ) {
		// SteakDataModel steak = new SteakDataModel();
		// steak.setAuthor( user.getNickname() );
		// steak.setDate( new Date() );
		// steak.setDueDate( dueDate );
		// steak.setName( "분류 전 노트 이름 " + i );
		// steak.setNote( "분류 전 노트 내용 : " + i );
		// steak.setStage( "분류 전" );
		// steak.setStartDate( new Date() );
		// steaks1.add( steak );
		// }
		//
		// ArrayList< SteakDataModel > steaks2 = new ArrayList< SteakDataModel
		// >();
		// for ( int i = 0; i < 4; i++ ) {
		// SteakDataModel steak = new SteakDataModel();
		// steak.setAuthor( user.getNickname() );
		// steak.setDate( new Date() );
		// steak.setDueDate( dueDate );
		// steak.setName( "진행 중 노트 이름 " + i );
		// steak.setNote( "진행 중 노트 내용 : " + i );
		// steak.setStage( "진행 중" );
		// steak.setStartDate( new Date() );
		// steaks2.add( steak );
		// }
		//
		// ArrayList< SteakDataModel > steaks3 = new ArrayList< SteakDataModel
		// >();
		// for ( int i = 0; i < 4; i++ ) {
		// SteakDataModel steak = new SteakDataModel();
		// steak.setAuthor( user.getNickname() );
		// steak.setDate( new Date() );
		// steak.setDueDate( dueDate );
		// steak.setName( "완료 노트 이름 " + i );
		// steak.setNote( "완료 노트 내용 : " + i );
		// steak.setStage( "완료" );
		// steak.setStartDate( new Date() );
		// steaks3.add( steak );
		// }
		//
		// PersistenceManager pm = PMF.get().getPersistenceManager();
		// try {
		// for ( int i = 0; i < 4; i++ ) {
		// pm.makePersistent( steaks1.get( i ) );
		// pm.makePersistent( steaks2.get( i ) );
		// pm.makePersistent( steaks3.get( i ) );
		// }
		// } finally {
		// pm.close();
		// }
		//
		// SteakStageModel stage1 = new SteakStageModel();
		// stage1.setStage( "분류 전" );
		// ArrayList< Key > steakKey1 = new ArrayList< Key >();
		// for ( SteakDataModel steak : steaks1 ) {
		// steakKey1.add( steak.getKey() );
		// }
		// stage1.setRows( steakKey1 );
		// SteakStageModel stage2 = new SteakStageModel();
		// stage2.setStage( "진행 중" );
		// ArrayList< Key > steakKey2 = new ArrayList< Key >();
		// for ( SteakDataModel steak : steaks2 ) {
		// steakKey2.add( steak.getKey() );
		// }
		// stage2.setRows( steakKey2 );
		// SteakStageModel stage3 = new SteakStageModel();
		// stage3.setStage( "완료" );
		// ArrayList< Key > steakKey3 = new ArrayList< Key >();
		// for ( SteakDataModel steak : steaks3 ) {
		// steakKey3.add( steak.getKey() );
		// }
		// stage3.setRows( steakKey3 );
		//
		// pm = PMF.get().getPersistenceManager();
		// try {
		// pm.makePersistent( stage1 );
		// pm.makePersistent( stage2 );
		// pm.makePersistent( stage3 );
		// } finally {
		// pm.close();
		// }

		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query qData = pm.newQuery( SteakDataModel.class );
		Query qPipeline = pm.newQuery( SteakPipelineModel.class );
		Query qStage = pm.newQuery( SteakStageModel.class );

		List< SteakDataModel > resultsData = null;
		List< SteakPipelineModel > resultsPipeline = null;
		List< SteakStageModel > resultsStage = null;

		try {
			resultsData = (List< SteakDataModel >) qData.execute();
			resultsPipeline = (List< SteakPipelineModel >) qPipeline.execute();
			resultsStage = (List< SteakStageModel >) qStage.execute();

			if ( resultsData.isEmpty() ) {
				model.addAttribute( "steakData", null );
			} else {
				model.addAttribute( "steakData", resultsData );
			}

			if ( resultsPipeline.isEmpty() ) {
				model.addAttribute( "steakPipeline", null );
			} else {
				model.addAttribute( "steakPipeline", resultsPipeline );
				for ( SteakPipelineModel pipe : resultsPipeline ) {
					pipelines.add( pipe.getPipeline() );
					for ( String stage : pipe.getStages() ) {
						stages.add( stage );
					}
					for ( String cols : pipe.getColumns() ) {
						columns.add( cols );
					}
				}
			}

			if ( resultsStage.isEmpty() ) {
				model.addAttribute( "steakStage", null );
			} else {
				model.addAttribute( "steakStage", resultsStage );
			}
		} finally {
			qData.closeAll();
			qPipeline.closeAll();
			qStage.closeAll();
			pm.close();
		}

		model.addAttribute( "pipelines", pipelines );
		model.addAttribute( "stages", stages );
		model.addAttribute( "columns", columns );

		return "steak";
	}
}
