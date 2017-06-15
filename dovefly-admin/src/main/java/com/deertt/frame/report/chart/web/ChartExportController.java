package com.deertt.frame.report.chart.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.deertt.frame.base.project.ApplicationConfig;
import com.deertt.frame.base.web.DvBaseController;
import com.deertt.frame.base.web.vo.HandleResult;
import com.deertt.frame.report.chart.svg.MyConverter;
import com.deertt.frame.report.chart.vo.ChartVo;

/**
 * SVG 转换类，实现将SVG文件转换为常见图片格式文件
 * 
 * @author Zhy
 * 
 *         publish on Highcharts中文网 http://www.hcharts.cn
 * 
 *         导出步骤： 1、接受页面提交的参数 （ 可以将参数打印出来以确保页面提交过来的代码不会乱码） 2、将svg代码保存为.svg文件
 *         （保存文件时注意编码） 3、执行转换函数，将.svg文件转换成目标文件 4、读取目标文件，并调用 浏览器下载
 */
@Controller
@RequestMapping("/chartExportController")
public class ChartExportController extends DvBaseController {
	
	// 导出函数
	@RequestMapping("/showChart")
	public String showChart(HttpServletRequest request) throws Exception {
		
		//Map<String, Object> chart = new HashMap<String, Object>();
		
		ChartVo chart = new ChartVo();
		chart.title.setText("实时温度曲线");
		chart.subtitle.setText("数据来源: WorldClimate.com");
		chart.exporting.setFilename("实时温度曲线");
		chart.yAxis.title.setText("温度 (°C)");
		Object[] categories = {"Jan", "Feb", "Mar", "Apr", "May", "Jun","Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
		chart.getXAxis().setCategories(categories);
		
		ChartVo.SeriesData[] series = new ChartVo.SeriesData[4];
		
		ChartVo.SeriesData sd0 = new ChartVo().new SeriesData();
		sd0.setName("东京");
		Double[] data0 = {7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6};
		sd0.setData(data0);
		series[0] = sd0;
		
		ChartVo.SeriesData sd1 = new ChartVo().new SeriesData();
		sd1.setName("纽约");
		Double[] data1 = {-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5};
		sd1.setData(data1);
		series[1] = sd1;
		
		ChartVo.SeriesData sd2 = new ChartVo().new SeriesData();
		sd2.setName("柏林");
		Double[] data2 = {-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0};
		sd2.setData(data2);
		series[2] = sd2;
			
		ChartVo.SeriesData sd3 = new ChartVo().new SeriesData();
		sd3.setName("伦敦");
		Double[] data3 = {3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8};
		sd3.setData(data3);
		series[3] = sd3;
		
		chart.setSeries(series);
		
		request.setAttribute("chart", chart);//这里填充要展示的数据
		request.setAttribute("chartJSON", JSONObject.toJSON(chart).toString());//这里填充要展示的数据
		System.out.println(JSONObject.toJSON(chart).toString());
		request.setAttribute("show", true);
		return "jsp/report/testModule/chartTest";
	}

	// 导出函数
	@RequestMapping("/exportChart")
	public void exportChart(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 宽度
		float width = request.getParameter("width") == null ? 800 : Float.parseFloat(request.getParameter("width"));
		
		// 缩放比例，这里并没有用到该参数详见API，http://www.hcharts.cn/api/index.php#exporting.scale
		String scale = request.getParameter("scale");

		// 导出类型
		String type = request.getParameter("type");

		// 文件名
		String filename = request.getParameter("filename");

		// SVG代码，官方默认是以文件形式上传，用jsp/servlet
		// 的request.getParameter(arg0)是无法获取该值的，所以这里利用Struts2来帮我们处理。更多详情我将在Highcharts中文论坛上详细说明
		String svg = request.getParameter("svg");

		/**
		 * 第二步：将svg代码保存为svg文件
		 */

		// 打印获取的参数，确保可以获取值且中文不会乱码，如果出现乱码，请将你的Highcharts页面的编码设置为UTF-8
//		 System.out.println(type + "\n" + filename + "\n" +svg + "\n" +
//		 width+"\n"+scale);

		// 获取项目的绝对路径
		String svgPath = ApplicationConfig.FILE_ROOT_PATH_SVG + "/temp";
		File svgPathFile = new File(svgPath);
		if (!svgPathFile.exists()) {
			svgPathFile.mkdirs();
		}

		// SVG临时文件名
		String temp = svgPath + "/" + System.currentTimeMillis() + (int) (Math.random() * 1000) + ".svg";

		// 将svg代码写入到临时文件中，文件后缀的.svg
		File svgTempFile = new File(temp);
		// 写入文件，注意文件编码
		OutputStreamWriter svgFileOsw = new OutputStreamWriter(new FileOutputStream(svgTempFile), "UTF-8");
		svgFileOsw.write(svg);
		svgFileOsw.flush();
		svgFileOsw.close();

		/**
		 * 第三步：调用转换函数，生成目标文件
		 */
		MyConverter mc = new MyConverter();
		// 调用转换函数，返回目标文件名
		String tempFilename = mc.conver(temp, svgPath, type, width);

		// 读取目标文件流，转换调用下载
		File resultFile = new File(svgPath + "/" + tempFilename);
		FileInputStream resultFileFi = new FileInputStream(resultFile);
		long l = resultFile.length();
		int k = 0;
		byte abyte0[] = new byte[65000];

		/**
		 * 第四步：调用浏览器下载
		 */

		// 调用下载
		response.setContentType("application/x-msdownload");
		response.setContentLength((int) l);
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename + "." + getExt(type), "UTF-8"));
		while ((long) k < l) {
			int j = resultFileFi.read(abyte0, 0, 65000);
			k += j;
			response.getOutputStream().write(abyte0, 0, j);
		}
		resultFileFi.close();

		// 转换成功后，删除临时文件
		svgTempFile.delete();
		resultFile.delete();
	}
	
	// 获取报表数据
	@ResponseBody
	@RequestMapping(value="/getDatas", produces={"application/json;charset=UTF-8"})
	public Object getDatas(HttpServletRequest request) throws Exception {
		HandleResult result = new HandleResult();
		result.setSuccess(true);
		result.setMessage("导入用户信息成功！");
		return JSONObject.toJSON(result);
	}
	
	private String getExt(String type){
		// 记录文件后缀
		String ext = "";

		// 更具传入的类型设置导出类型和文件后缀
		if (type.equals("image/png")) {
			ext = "png";
		} else if (type.equals("image/jpeg")) {
			ext = "jpg";
		} else if (type.equals("application/pdf")) {
			ext = "pdf";
		} else if (type.equals("image/svg+xml")) {
			ext = "tif";
		} else {
			return "";
		}
		return ext;
	}

}
