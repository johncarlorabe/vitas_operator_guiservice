package com.tlc.sky.pingen.c;

import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;
import com.tlc.sky.pingen.m.RegisteredPingenGroup;
import com.tlc.sky.pingen.v.SearchView;

public class GroupDetailsCommand extends UICommand {

	@Override
	public IView execute() {
		String name = this.params.get("GROUPNAME");
		
//    ExistingSession sess = null;
//    
//    SessionView v = null;
//    
//    try{
//     
//    sess = ExistingSession.parse(this.reqHeaders);
//    
//
//    if(sess.exists()) {

		RegisteredPingenGroup pingenGroup = new RegisteredPingenGroup(name);
		//pingenGroup.setAuthorizedSession(sess);
		
		try{
			
			if(pingenGroup.exists()){

				ObjectState state = new ObjectState("00","Successfull");
				pingenGroup.setState(state);
				SearchView view = new SearchView(pingenGroup);
				return view;
			}
			else{
				ObjectState state = new ObjectState("01","Unsuccessfull");
				pingenGroup.setState(state);
				SearchView view = new SearchView(pingenGroup);
				return view;
			}
		}catch(Exception e){
			ObjectState state = new ObjectState("02","Unsuccessfull");
			pingenGroup.setState(state);
			SearchView view = new SearchView(pingenGroup);
			return view;
			
		}
//    }else{
//        UISession u = new UISession(null);
//     u.setState(new ObjectState("TLC-3902-01"));
//     v = new SessionView(u);
//      
//   }
//   
//}catch (SessionNotFoundException e) {
//    UISession u = new UISession(null);
//    u.setState(new ObjectState("TLC-3902-01"));
//    v = new SessionView(u);
//   
//  }
//return v;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return "GROUPDETAILS";
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 3020;
	}

}
