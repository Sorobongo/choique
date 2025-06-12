package com.northpole.snow.base.ui.component;

import java.io.Serializable;

public interface IEntityForm extends Serializable {
	void init();
	void edit();
	void showNotification(String msg);
		
}
