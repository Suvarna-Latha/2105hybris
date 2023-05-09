/**
 *
 */
package com.gpt.service.productDetails;

import java.io.IOException;


/**
 * @author Nishikanth Reddy
 *
 */
public interface ProductDetailsService
{
	String getProductDescription(String ProductName) throws IOException;

	String getProductImages(String ProductName) throws Exception;

	String getTranslatedMessage(String contentToTranslate) throws Exception;

}
