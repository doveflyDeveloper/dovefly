package com.deertt.frame.report.chart.vo;


public class ChartVo {

	public Title title = new Title();
	public Subtitle subtitle = new Subtitle();
	public XAxis xAxis = new XAxis();
	public YAxis yAxis = new YAxis();
	public Tooltip tooltip = new Tooltip();
	public Exporting exporting = new Exporting();
	public SeriesData[] series;

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public Subtitle getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(Subtitle subtitle) {
		this.subtitle = subtitle;
	}

	public XAxis getXAxis() {
		return xAxis;
	}

	public void setXAxis(XAxis xAxis) {
		this.xAxis = xAxis;
	}

	public YAxis getYAxis() {
		return yAxis;
	}

	public void setYAxis(YAxis yAxis) {
		this.yAxis = yAxis;
	}

	public Tooltip getTooltip() {
		return tooltip;
	}

	public void setTooltip(Tooltip tooltip) {
		this.tooltip = tooltip;
	}

	public Exporting getExporting() {
		return exporting;
	}

	public void setExporting(Exporting exporting) {
		this.exporting = exporting;
	}

	public SeriesData[] getSeries() {
		return series;
	}

	public void setSeries(SeriesData[] series) {
		this.series = series;
	}

	public class Title {
		public String text;

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}
	}

	public class Subtitle {
		public String text;

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}
	}

	public class XAxis {
		public Object[] categories;

		public Object[] getCategories() {
			return categories;
		}

		public void setCategories(Object[] categories) {
			this.categories = categories;
		}
	}

	public class YAxis {
		public Title title = new Title();

		public Title getTitle() {
			return title;
		}

		public void setTitle(Title title) {
			this.title = title;
		}
	}

	public class Tooltip {
		public String valueSuffix;

		public String getValueSuffix() {
			return valueSuffix;
		}

		public void setValueSuffix(String valueSuffix) {
			this.valueSuffix = valueSuffix;
		}
	}

	public class Exporting {
		public String filename;

		public String getFilename() {
			return filename;
		}

		public void setFilename(String filename) {
			this.filename = filename;
		}
	}

	public class SeriesData {
		public String name;
		public Object[] data = {};

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Object[] getData() {
			return data;
		}

		public void setData(Object[] data) {
			this.data = data;
		}
	}

}
