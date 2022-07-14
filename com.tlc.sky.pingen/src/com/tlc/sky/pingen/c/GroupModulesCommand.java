package com.tlc.sky.pingen.c;

import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;
import com.tlc.sky.pingen.m.GroupModulesCollection;
import com.tlc.sky.pingen.v.GroupModulesCollectionView;
public class GroupModulesCommand extends UICommand{

	@Override
	public IView execute() {
		
//	ExistingSession sess = null;
//    
//    SessionView v = null;
    
//    try{
     
     
//   sess = ExistingSession.parse(this.reqHeaders);
//    
//	    
//	    
//	if(sess.exists()) {
				String id = this.params.get("GROUPID");
				
				ModelCollection ret = null;
				GroupModulesCollection d = new GroupModulesCollection(id);
	//			d.setAuthorizedSession(sess);
				if(d.hasRows()){
					ret = d;
				}
				GroupModulesCollectionView res = new GroupModulesCollectionView("00",ret);
				return res;
    }		
	
//   }else{
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
//	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return "ROLEMODULES";
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 3030;
	}




}
