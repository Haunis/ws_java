package com.jiage.excel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.jiage.utils.FileUtils;

public class ExcelDao {
	public static final String VALUE = "数值";
	public static final String SECTION = "部门";
	private int mSectionIndex;
	private int mValueIndex;
	private ResultListener mListener;
	private static final double TOP_FACTOR = 0.2;// 前20%
	private static final double BOT_FACTOR = 0.2;// 后20%

	public ExcelDao(ResultListener listener) {
		this.mListener = listener;
	}

	public void analyzeExcel() {
		try {
			File file = FileUtils.findExcelFile();
			if (file == null) {
				mListener.onError("No .xls File");
				return;
			}
			mListener.onError("start to read");
			List<Person> rowList = readExcel(file);

			String destFileName = "result_" + file.getName();
			mListener.onError("start to write");
			writeExcel(getSortList(rowList), destFileName);
		} catch (IOException e1) {
			mListener.onError("analyze IOException");
			e1.printStackTrace();
		} catch (Exception e) {
			mListener.onError(e.toString());
			e.printStackTrace();
		}
	}

	private List<Person> getSortList(List<Person> rowList) {
		HashMap<String, List<Person>> map = new HashMap<String, List<Person>>();
		for (int i = 1; i < rowList.size(); i++) {
			Person p = rowList.get(i);
			List<Person> tempList = map.get(p.section);
			if (tempList == null) {
				List<Person> list = new ArrayList<Person>();
				list.add(p);
				map.put(p.section, list);
			} else {
				tempList.add(p);
			}
		}
		List<Person> sortedList = new ArrayList<Person>();
		sortedList.add(rowList.get(0));
		for (Entry<String, List<Person>> entry : map.entrySet()) {
			List<Person> list = entry.getValue();
			Collections.sort(list);
//			System.out.println(list);
			int topIndex = (int) Math.round(list.size() * TOP_FACTOR) - 1; // 前20%的索引
			int bottomIndex = (int) Math.round(list.size() * (1 - BOT_FACTOR));// 后20的索引
			if (topIndex >= 0) {
				for (int i = 0; i <= topIndex; i++) {
					list.get(i).isTopRanked = true;
				}
			}
			for (int i = bottomIndex; i < list.size(); i++) {
				list.get(i).isBotRanked = true;
			}
			sortedList.addAll(list);
		}
		return sortedList;
	}

	/**
	 * 读取Excel的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行
	 * 
	 * @param file                读取数据的源Excel
	 * @param ignoreRows读取数据忽略的行数 ，比如行头不需要读入 忽略的行数为1
	 * @return 读出的Excel中数据的内容
	 * @throws Exception
	 */

	public List<Person> readExcel(File file) throws Exception {
		List<Person> rowList = new ArrayList<Person>();
		int rowSize = 0;
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		// 打开HSSFWorkbook
		POIFSFileSystem fs = new POIFSFileSystem(in);
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		HSSFCell cell = null;
		// 遍历所有Sheet,这里只取第一张Sheet
		for (int sheetIndex = 0; sheetIndex < /* wb.getNumberOfSheets() */1; sheetIndex++) {
			HSSFSheet st = wb.getSheetAt(sheetIndex);
			for (int rowIndex = 0; rowIndex <= st.getLastRowNum(); rowIndex++) {
				HSSFRow row = st.getRow(rowIndex);
				if (row == null) {
					continue;
				}
				int tempRowSize = row.getLastCellNum() + 1;
				if (tempRowSize > rowSize) {
					rowSize = tempRowSize;
				}
				String[] values = new String[rowSize];
				Arrays.fill(values, "");
				boolean hasValue = false;
				for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
					String value = "";
					cell = row.getCell(columnIndex);
					if (cell != null) {
						// 注意：一定要设成这个，否则可能会出现乱码
						cell.setEncoding(HSSFCell.ENCODING_UTF_16);
						switch (cell.getCellType()) {
						case HSSFCell.CELL_TYPE_STRING:
							value = cell.getStringCellValue();
							break;
						case HSSFCell.CELL_TYPE_NUMERIC:
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								Date date = cell.getDateCellValue();
								if (date != null) {
									value = new SimpleDateFormat("yyyy-MM-dd").format(date);
								} else {
									value = "";
								}
							} else {
								value = new DecimalFormat("0").format(cell.getNumericCellValue());
							}
							break;
						case HSSFCell.CELL_TYPE_FORMULA:
							// 导入时如果为公式生成的数据则无值
							if (!cell.getStringCellValue().equals("")) {
								value = cell.getStringCellValue();
							} else {
								value = cell.getNumericCellValue() + "";
							}
							break;
						case HSSFCell.CELL_TYPE_BLANK:
							break;
						case HSSFCell.CELL_TYPE_ERROR:
							value = "";
							break;
						case HSSFCell.CELL_TYPE_BOOLEAN:
							value = (cell.getBooleanCellValue() == true ? "Y" : "N");
							break;
						default:
							value = "";
						}
					}
					if (columnIndex == 0 && value.trim().equals("")) {
						break;
					}
					values[columnIndex] = rightTrim(value);
					hasValue = true;
				}
				if (hasValue) {
					Person person;
					if (rowIndex == 0) {
						mSectionIndex = getSectionIndex(values);
						mValueIndex = getValueIndex(values);
						if (mSectionIndex == -1 || mValueIndex == -1) {
//							mListener.onReadError("确认分类里有【" + SECTION + "】和【" + VALUE + "】选项");
							mListener.onError("make sure there is SECTION and COUNT in the sheet");
							throw new Exception("no section or value");
						}
						person = new Person(values, null, 0);
					} else {
						int value = Integer.parseInt(values[mValueIndex]);
						person = new Person(values, values[mSectionIndex], value);
					}
					rowList.add(person);
				}
			}
		}
		in.close();
//		String[][] returnArray = new String[rowList.size()][rowSize];
//		for (int i = 0; i < returnArray.length; i++) {
//			returnArray[i] = (String[]) rowList.get(i);
//		}
//		return returnArray;
		return rowList;
	}

	/**
	 * 
	 * 去掉字符串右边的空格
	 * 
	 * @param str 要处理的字符串
	 * @return 处理后的字符串
	 */

	public String rightTrim(String str) {
		if (str == null) {
			return "";
		}
		int length = str.length();
		for (int i = length - 1; i >= 0; i--) {
			if (str.charAt(i) != 0x20) {
				break;
			}
			length--;
		}
		return str.substring(0, length);
	}

	public void writeExcel(List<Person> list, String destFileName) {
		try {
			File file = new File("./" + destFileName);
			FileOutputStream out = new FileOutputStream(file);
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			HSSFCell cell = null;

			HSSFCellStyle csTopRanked = workbook.createCellStyle();
			HSSFCellStyle csBotRanked = workbook.createCellStyle();
			HSSFCellStyle cs2 = workbook.createCellStyle();
			HSSFCellStyle csBotLine = workbook.createCellStyle();
			HSSFDataFormat df = workbook.createDataFormat();

			HSSFFont fontTopRanked = workbook.createFont();
			HSSFFont fontBotRanked = workbook.createFont();
			HSSFFont font2 = workbook.createFont();

			// set font 1 to 12 point type
			fontTopRanked.setFontHeightInPoints((short) 10);
//			font1.setColor((short) 0xc);//蓝色
			fontTopRanked.setColor((short) 0xc);
//			font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// arial is the default font

			fontBotRanked.setFontHeightInPoints((short) 10);
			fontBotRanked.setColor(HSSFFont.COLOR_RED);

			// set font 2 to 10 point type
			font2.setFontHeightInPoints((short) 10);
			font2.setColor(HSSFFont.COLOR_RED);
			font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font2.setStrikeout(true);

			csTopRanked.setFont(fontTopRanked);
			csTopRanked.setDataFormat(df.getFormat("#,##0.0"));
			csBotRanked.setFont(fontBotRanked);

			// set a thin border
			cs2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			// fill w fg fill color
			cs2.setFillPattern((short) HSSFCellStyle.SOLID_FOREGROUND);
			// set the cell format to text see DataFormat for a full list
			cs2.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));

			// set the font
			cs2.setFont(font2);

			workbook.setSheetName(0, "FirstSheet");

			HSSFRow row = null;
			int rowNum;
			for (rowNum = 0; rowNum < list.size(); rowNum++) {
				row = sheet.createRow(rowNum);
				// make the row height bigger (in twips - 1/20 of a point)
//				row.setHeight((short) 0x249);

//				 row.setRowNum(rowNum);
				Person person = list.get(rowNum);
				for (short columnNum = (short) 0; columnNum < list.get(0).infos.length; columnNum++) {
					cell = row.createCell(columnNum);
					cell.setEncoding(HSSFCell.ENCODING_UTF_16);

					if (person.isTopRanked) {
						cell.setCellStyle(csTopRanked);
					}
					if (person.isBotRanked) {
						cell.setCellStyle(csBotRanked);
					}
					String value = person.infos[columnNum];
					if (rowNum != 0 && columnNum == mValueIndex) {
						int intValue = Integer.parseInt(value);
						cell.setCellValue(intValue);
					} else {
						cell.setCellValue(value);
					}
					// 第一个参数为索引，第二个参数为宽度
//					sheet.setColumnWidth((short) (cellnum + 1), (short) ((50 * 8) / ((double) 1 / 20)));
				}
			}

			// draw a thick black border at the bottom using BLANKS;advance 2 rows
			rowNum++;
			rowNum++;

			row = sheet.createRow(rowNum);

			// define the third style to be the default
			// except with a thick black border at the bottom
			csBotLine.setBorderBottom(HSSFCellStyle.BORDER_THICK);

			// create 50 cells
			for (short cellnum = (short) 0; cellnum < 50; cellnum++) {
				// create a blank type cell (no value)
				cell = row.createCell(cellnum);
				cell.setCellStyle(csBotLine);
			}
			// demonstrate adding/naming and deleting a sheet
			// create a sheet, set its title then delete it
			sheet = workbook.createSheet();
			workbook.setSheetName(1, "DeletedSheet");
			workbook.removeSheetAt(1);

			workbook.write(out);
			out.close();
			mListener.onComplete();
		} catch (FileNotFoundException e) {
			mListener.onError("File Not Found");
			e.printStackTrace();
		} catch (IOException e) {
			mListener.onError("IOException");
			e.printStackTrace();
		}
	}

	/**
	 * 获取“部门”所在的列索引
	 */
	public int getSectionIndex(String[] values) {
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				if (SECTION.equals(values[i])) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * 获取“数值”所在的列索引
	 */
	public int getValueIndex(String[] values) {
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				if (VALUE.equals(values[i])) {
					return i;
				}
			}
		}
		return -1;
	}
}
