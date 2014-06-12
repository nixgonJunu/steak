package com.nixgon.steak;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.appengine.api.datastore.Key;
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

		SteakPipelineModel steakPipe = new SteakPipelineModel();
		columns.add( "Stage" );
		columns.add( "Name" );
		columns.add( "Note" );
		columns.add( "Author" );
		columns.add( "Start date" );
		columns.add( "Due date" );
		steakPipe.setColumns( columns );
		steakPipe.setPipeline( "Dummy Pipeline" );
		stages.add( "Before categorized" );
		stages.add( "Work in progress" );
		stages.add( "Finished" );
		steakPipe.setStages( stages );

		// Calendar c = Calendar.getInstance();
		// c.add( Calendar.DATE, 7 );
		// Date dueDate = c.getTime();
		//
		// ArrayList< SteakDataModel > steaks1 = new ArrayList< SteakDataModel
		// >();
		// for ( int i = 0; i < 2; i++ ) {
		// SteakDataModel steak = new SteakDataModel();
		// steak.setAuthor( user.getNickname() );
		// steak.setDate( new Date() );
		// steak.setDueDate( dueDate );
		// steak.setName( "Before categorized " + i );
		// steak.setNote( "Before categorized : " + i );
		// steak.setStage( "Before categorized" );
		// steak.setStartDate( new Date() );
		// steaks1.add( steak );
		// }
		//
		// ArrayList< SteakDataModel > steaks2 = new ArrayList< SteakDataModel
		// >();
		// for ( int i = 0; i < 2; i++ ) {
		// SteakDataModel steak = new SteakDataModel();
		// steak.setAuthor( user.getNickname() );
		// steak.setDate( new Date() );
		// steak.setDueDate( dueDate );
		// steak.setName( "Work in progress " + i );
		// steak.setNote( "Work in progress : " + i );
		// steak.setStage( "Work in progress" );
		// steak.setStartDate( new Date() );
		// steaks2.add( steak );
		// }
		//
		// ArrayList< SteakDataModel > steaks3 = new ArrayList< SteakDataModel
		// >();
		// for ( int i = 0; i < 2; i++ ) {
		// SteakDataModel steak = new SteakDataModel();
		// steak.setAuthor( user.getNickname() );
		// steak.setDate( new Date() );
		// steak.setDueDate( dueDate );
		// steak.setName( "Finished " + i );
		// steak.setNote( "Finished : " + i );
		// steak.setStage( "Finished" );
		// steak.setStartDate( new Date() );
		// steaks3.add( steak );
		// }
		//
		// PersistenceManager pm = PMF.get().getPersistenceManager();
		// try {
		// pm.makePersistent( steakPipe );
		// for ( int i = 0; i < steaks1.size(); i++ ) {
		// pm.makePersistent( steaks1.get( i ) );
		// pm.makePersistent( steaks2.get( i ) );
		// pm.makePersistent( steaks3.get( i ) );
		// }
		// } finally {
		// pm.close();
		// }

		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery( SteakDataModel.class );

		List< SteakDataModel > data = null;

		try {
			data = (List< SteakDataModel >) q.execute();

			SteakStageModel stage1 = new SteakStageModel();
			stage1.setStage( "Before categorized" );
			ArrayList< Key > steakKey1 = new ArrayList< Key >();

			SteakStageModel stage2 = new SteakStageModel();
			stage2.setStage( "Work in progress" );
			ArrayList< Key > steakKey2 = new ArrayList< Key >();

			SteakStageModel stage3 = new SteakStageModel();
			stage3.setStage( "Finished" );
			ArrayList< Key > steakKey3 = new ArrayList< Key >();

			for ( SteakDataModel steak : data ) {
				if ( steak.getStage().equals( stage1.getStage() ) ) {
					steakKey1.add( steak.getKey() );
				} else if ( steak.getStage().equals( stage2.getStage() ) ) {
					steakKey2.add( steak.getKey() );
				} else if ( steak.getStage().equals( stage3.getStage() ) ) {
					steakKey3.add( steak.getKey() );
				}
			}

			stage1.setRows( steakKey1 );
			stage2.setRows( steakKey2 );
			stage3.setRows( steakKey3 );

			pm.makePersistent( stage1 );
			pm.makePersistent( stage2 );
			pm.makePersistent( stage3 );
		} finally {
			pm.close();
		}

		pm = PMF.get().getPersistenceManager();
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

			if ( resultsPipeline.isEmpty() ) {
				model.addAttribute( "steakPipeline", null );
			} else {
				model.addAttribute( "steakPipeline", resultsPipeline );
				model.addAttribute( "pipelines", pipelines );
				setPipelineInfo( pipelines, stages, columns, resultsPipeline );
			}

			if ( resultsStage.isEmpty() ) {
				model.addAttribute( "steakStage", null );
			} else {
				model.addAttribute( "steakStage", resultsStage );
				model.addAttribute( "stages", stages );
			}

			if ( resultsData.isEmpty() ) {
				model.addAttribute( "steakData", null );
			} else {
				model.addAttribute( "steakData", resultsData );
				model.addAttribute( "columns", columns );
			}
		} finally {
			qData.closeAll();
			qPipeline.closeAll();
			qStage.closeAll();
			pm.close();
		}

		return "steak";
	}

	private void setPipelineInfo( ArrayList< String > pipelines, ArrayList< String > stages, ArrayList< String > columns,
			List< SteakPipelineModel > resultsPipeline ) {
		pipelines.clear();
		stages.clear();
		columns.clear();
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
}
