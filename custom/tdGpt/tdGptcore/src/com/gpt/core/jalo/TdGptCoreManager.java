/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.gpt.core.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import com.gpt.core.constants.TdGptCoreConstants;
import com.gpt.core.setup.CoreSystemSetup;


/**
 * Do not use, please use {@link CoreSystemSetup} instead.
 * 
 */
public class TdGptCoreManager extends GeneratedTdGptCoreManager
{
	public static final TdGptCoreManager getInstance()
	{
		final ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (TdGptCoreManager) em.getExtension(TdGptCoreConstants.EXTENSIONNAME);
	}
}
