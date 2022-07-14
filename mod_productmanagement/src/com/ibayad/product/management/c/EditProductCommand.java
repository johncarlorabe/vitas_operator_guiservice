package com.ibayad.product.management.c;

import java.text.ParseException;

import com.ibayad.product.management.m.AuditTrail;
import com.ibayad.product.management.m.ManageProduct;
import com.ibayad.product.management.v.JsonView;
import com.tlc.common.LongUtil;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

@SuppressWarnings("unused")
public class EditProductCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;	    
	    SessionView v = null;	    
	    try{
	    	sess = ExistingSession.parse(this.reqHeaders);	    
			  if(sess.exists()) {
					String type = this.params.get("Type");
					String category = this.params.get("Category");
					String productinterface = this.params.get("ProductInterface");
					String name = this.params.get("Name");
					String description = this.params.get("Description");
					Long amount = LongUtil.toLong(this.params.get("Amount"));
					String sku = this.params.get("Sku");
					String id = this.params.get("Id");
					String provider = this.params.get("Provider");
					
					ManageProduct create = new ManageProduct();

					create.setAuthorizedSession(sess);
					create.setType(type);
					create.setCategory(category);	
					create.setProductinterface(productinterface);
					create.setName(name);
					create.setDescription(description);
					create.setAmount(amount);
					create.setSku(sku);
					create.setId(id);
					create.setProvider(provider);
						
					if(create.update()){
							ObjectState state = new ObjectState("00","Successfully updated product");
							create.setState(state);
							AuditTrail audit  = new AuditTrail();
					    	audit.setIp(create.getAuthorizedSession().getIpAddress());
					    	audit.setModuleid(String.valueOf(this.getId()));
					    	audit.setEntityid(type+"|"+category+"|"+productinterface+"|"+name+"|"+description+"|"+amount+"|"+sku+"|"+id);
					    	audit.setLog(create.getState().getMessage());
					    	audit.setStatus(create.getState().getCode());
					    	audit.setUserid(create.getAuthorizedSession().getAccount().getId());
					    	audit.setUsername(create.getAuthorizedSession().getAccount().getUserName());
					    		
					    	audit.insert();
							JsonView view = new JsonView(create);
							return view;
					}else{
							ObjectState state = new ObjectState("01","Unable to update product");
							create.setState(state);
							AuditTrail audit  = new AuditTrail();
					    	audit.setIp(create.getAuthorizedSession().getIpAddress());
					    	audit.setModuleid(String.valueOf(this.getId()));
					    	audit.setEntityid(type+"|"+category+"|"+productinterface+"|"+name+"|"+description+"|"+amount+"|"+sku+"|"+id);
					    	audit.setLog(create.getState().getMessage());
					    	audit.setStatus(create.getState().getCode());
					    	audit.setUserid(create.getAuthorizedSession().getAccount().getId());
					    	audit.setUsername(create.getAuthorizedSession().getAccount().getUserName());
					    		
					    	audit.insert();
							JsonView view = new JsonView(create);
							return view;
					}
						
					
			  }else{
			      UISession u = new UISession(null);
			   u.setState(new ObjectState("TLC-3902-01"));
			   v = new SessionView(u);
			    
			 }
 
	    	}catch (SessionNotFoundException e) {
				  UISession u = new UISession(null);
				  u.setState(new ObjectState("TLC-3902-01"));
				  v = new SessionView(u);
	    	} catch (ParseException e) {
				e.printStackTrace();
				  UISession u = new UISession(null);
				  u.setState(new ObjectState("TLC-3902-01"));
				  v = new SessionView(u);
			}
	    	return v;
	}

	@Override
	public String getKey() {
		return "UPDATEPRODUCTWL";
	}

	@Override
	public int getId() {
		return 9502;
	}

}
