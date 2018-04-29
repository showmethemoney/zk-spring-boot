package zk.springboot.charts.candlestick;

import java.util.Date;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.Area;
import org.zkoss.zul.HiLoModel;
import org.zkoss.zul.SimpleHiLoModel;
import zk.springboot.charts.candlestick.model.ChartData;

public class CandlestickChartVM {

    protected static final Logger logger = LoggerFactory.getLogger(CandlestickChartVM.class);
    HiLoModel model;
    CandlestickChartEngine engine;
    String message;

    @Init
    public void init() {
        engine = new CandlestickChartEngine();

        model = new SimpleHiLoModel();
        Iterator<Object[]> it = ChartData.get().iterator();
        while (it.hasNext()) {
            Object[] data = it.next();
            model.addValue((Date) data[0], (Double) data[1], (Double) data[2], (Double) data[3], (Double) data[4], (Double) data[5]);
        }
    }

    public CandlestickChartEngine getEngine() {
        return engine;
    }

    public HiLoModel getModel() {
        return model;
    }

    public String getMessage() {
        return message;
    }

    @Command("showMessage")
    @NotifyChange("message")
    public void onShowMessage(@BindingParam("msg") String message) {
        this.message = message;
    }
}
