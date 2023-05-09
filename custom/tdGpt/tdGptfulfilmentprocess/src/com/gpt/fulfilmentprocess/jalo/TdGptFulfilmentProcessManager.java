/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.gpt.fulfilmentprocess.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import com.gpt.fulfilmentprocess.constants.TdGptFulfilmentProcessConstants;

public class TdGptFulfilmentProcessManager extends GeneratedTdGptFulfilmentProcessManager
{
	public static final TdGptFulfilmentProcessManager getInstance()
	{
		ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (TdGptFulfilmentProcessManager) em.getExtension(TdGptFulfilmentProcessConstants.EXTENSIONNAME);
	}
	
}
