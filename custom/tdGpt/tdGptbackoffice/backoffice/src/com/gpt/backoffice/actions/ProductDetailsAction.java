/**
 *
 */
package com.gpt.backoffice.actions;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.gpt.service.productDetails.ProductDetailsService;
import com.hybris.backoffice.widgets.notificationarea.event.NotificationEvent.Level;
import com.hybris.cockpitng.actions.ActionContext;
import com.hybris.cockpitng.actions.ActionResult;
import com.hybris.cockpitng.actions.CockpitAction;
import com.hybris.cockpitng.util.notifications.NotificationService;




/**
 * @author Suvarna Latha
 *
 */
public class ProductDetailsAction implements CockpitAction<Object, Object>
{

	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "productDetailsService")
	private ProductDetailsService productDetailsService;

	@Resource(name = "notificationService")
	private NotificationService notificationService;

	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	private static final Logger LOG = Logger.getLogger(ProductDetailsAction.class);

	@Resource(name = "i18NService")
	private I18NService i18NService;



	@Override
	public ActionResult<Object> perform(final ActionContext<Object> ctx)
	{
		final Object data = ctx.getData();
		ProductModel productModel = null;
		if (data instanceof ProductModel)
			{
				productModel = (ProductModel) data;
				try
				{
					final Optional<String> productName = Optional.ofNullable(productModel.getName());
					final Optional<String> productSummary = Optional.ofNullable(productModel.getSummary());
					final Optional<String> productManufacturerTypeDescription = Optional
							.ofNullable(productModel.getManufacturerTypeDescription());
					final Optional<String> productSegment = Optional.ofNullable(productModel.getSegment());
					final Optional<String> productRemarks = Optional.ofNullable(productModel.getRemarks());
					final Optional<String> productdescription = Optional
							.ofNullable(productDetailsService.getProductDescription(productModel.getName()));
					final Optional<String> productImages = Optional
							.ofNullable(productDetailsService.getProductImages(productModel.getName()));
					if (productImages.isPresent())
					{
						final String image = productImages.get();
						getMediaUploadStatus(image, productModel);
					}
					if (productSummary.isPresent())
					{
						final Optional<Map<Locale, String>> summaryTranslations = Optional
								.ofNullable(getTranslatedValues(productModel.getSummary()));
						if (summaryTranslations.isPresent())
						{
							setSummaryTranslations(summaryTranslations.get(), productModel);

						}
					}
					if (productManufacturerTypeDescription.isPresent())
					{
						final Optional<Map<Locale, String>> manufacturerTypeDescriptionTranslations = Optional
								.ofNullable(getTranslatedValues(productModel.getManufacturerTypeDescription()));
						if (manufacturerTypeDescriptionTranslations.isPresent())
						{
							setManufacturerTypeDescription(manufacturerTypeDescriptionTranslations.get(), productModel);

						}
					}
					if (productSegment.isPresent())
					{
						final Optional<Map<Locale, String>> segmentTranslations = Optional
								.ofNullable(getTranslatedValues(productModel.getSegment()));
						if (segmentTranslations.isPresent())
						{
							setSegment(segmentTranslations.get(), productModel);
						}

					}
					if (productRemarks.isPresent())
					{
						final Optional<Map<Locale, String>> remarksTranslation = Optional
								.ofNullable(getTranslatedValues(productModel.getRemarks()));
						if (remarksTranslation.isPresent())
						{
							setRemarks(remarksTranslation.get(), productModel);
						}
					}
					if (productdescription.isPresent())
					{
						productModel.setDescription(productdescription.get());
					}
					if (productName.isPresent())
					{
						final Optional<Map<Locale, String>> nameTranslation = Optional
								.ofNullable(getTranslatedValues(productModel.getName()));
						if (nameTranslation.isPresent())
						{
							setName(nameTranslation.get(), productModel);

						}
					}
					notificationService.notifyUser(notificationService.getWidgetNotificationSource(ctx), "productDetailsSaveSucess",
							Level.SUCCESS, new Object[]
							{ productModel });
					return new ActionResult("success", productModel);


				}
				catch (final Exception e)
				{
					e.printStackTrace();
					notificationService.notifyUser(notificationService.getWidgetNotificationSource(ctx), "productDetailsSaveFailure",
							Level.FAILURE, new Object[]
							{ data });
					return new ActionResult("failure", productModel);
				}

			}


		return new ActionResult("unknown", productModel);

	}

	/**
	 * @param map
	 * @param productModel
	 */
	private void setRemarks(final Map<Locale, String> remarksTranslation, final ProductModel productModel)
	{
		for (final Map.Entry<Locale, String> entry : remarksTranslation.entrySet())
		{
			productModel.setRemarks(entry.getValue(), entry.getKey());
		}
		modelService.save(productModel);

	}

	private void setName(final Map<Locale, String> nameTranslation, final ProductModel productModel)
	{
		for (final Map.Entry<Locale, String> entry : nameTranslation.entrySet())
		{
			productModel.setName(entry.getValue(), entry.getKey());
		}
		modelService.save(productModel);

	}


	private void setSegment(final Map<Locale, String> segmentTranslations, final ProductModel productModel)
	{
		for (final Map.Entry<Locale, String> entry : segmentTranslations.entrySet())
		{
			productModel.setSegment(entry.getValue(), entry.getKey());
		}
		modelService.save(productModel);

	}


	private void setManufacturerTypeDescription(final Map<Locale, String> manufacturerTypeDescriptionTranslations,
			final ProductModel productModel)
	{
		for (final Map.Entry<Locale, String> entry : manufacturerTypeDescriptionTranslations.entrySet())
		{
			productModel.setManufacturerTypeDescription(entry.getValue(), entry.getKey());
		}
		modelService.save(productModel);

	}


	private void setDescriptionTranslation(final Map<Locale, String> descriptionTranslation, final ProductModel productModel)
	{
		for (final Map.Entry<Locale, String> entry : descriptionTranslation.entrySet())
		{
			productModel.setDescription(entry.getValue(), entry.getKey());
		}
		modelService.save(productModel);
	}


	private void setSummaryTranslations(final Map<Locale, String> summaryTranslations, final ProductModel productModel)
	{
		for (final Map.Entry<Locale, String> entry : summaryTranslations.entrySet())
		{
			productModel.setSummary(entry.getValue(), entry.getKey());
		}
		modelService.save(productModel);

	}

	void getMediaUploadStatus(final String imageUrl, final ProductModel productModel)
	{
		final List<String> listOfProductImages = new ArrayList<>();
		listOfProductImages.add(imageUrl);
		productModel.setProductImages(listOfProductImages);
		modelService.saveAll(productModel);
		modelService.refresh(productModel);
	}

	Map<Locale, String> getTranslatedValues(final String value) throws Exception
	{
		final Set<Locale> locales = i18NService.getSupportedLocales();
		final List<Locale> arr = new ArrayList<>(locales);
		final String translatedMessage = productDetailsService.getTranslatedMessage(value);
		if (translatedMessage != null)
		{
			final List<String> lines = new ArrayList<>();
			final Map<Locale, String> localeAndValue = new HashMap<Locale, String>();
			translatedMessage.lines().forEach(s -> lines.add(s));
			final List<String> values = new ArrayList<>();
			for (final String line : lines)
			{
				final StringBuilder sb = new StringBuilder(line);
				sb.delete(0, 3);
				if (!sb.toString().isEmpty())
				{
					values.add(sb.toString());
				}
			}
			for (int i = 0; i < locales.size(); i++)
			{
				localeAndValue.put(arr.get(i), values.get(i));
			}
			return localeAndValue;
		}
		else
		{
			return null;
		}

	}



}
