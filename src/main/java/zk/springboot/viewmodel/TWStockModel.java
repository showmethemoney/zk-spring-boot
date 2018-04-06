package zk.springboot.viewmodel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

import zk.springboot.service.TWStockService;

@VariableResolver(DelegatingVariableResolver.class)
public class TWStockModel
{
	protected static final Logger logger = LoggerFactory.getLogger( TWStockModel.class );
	@WireVariable
	private TWStockService stockService = null;
	
	
}
