package com.vege.service.analysis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


@Repository
public class CalcToMonth {

    private final JdbcTemplate jdbcTemplate;

    int countTotal = 0;

    @Autowired
    public CalcToMonth(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * @param pname     名称
     * @param pyear     年
     * @param pmonth    月
     * @param pclassify 类别
     * @return
     */
    public double cal_price(String pname, int pyear, int pmonth, String pclassify) {
        ResultSet rs = select_price(pname, pyear, pmonth, pclassify);
        int count = 0;
        double sum = 0;

        if (rs == null) {
            System.out.println("cal_p ERROR !");
        }
        try {
            while (rs.next()) {
                sum += rs.getDouble("mprice");  // 4:mprice  5:hprice
                count++;
                countTotal++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        double mean_price;
        if (count == 0 || sum == 0)
            mean_price = 0;
        else
            mean_price = sum / count;

        System.out.println(pname + "  规格-" + pclassify + " : " + pyear + " 年" + pmonth + " 月" + " 共\t" + count + " 条记录, 总计 " + sum + ", 平均值是\t" + mean_price);
        return mean_price;

    }


    /**
     * @param pname  名称
     * @param pyear  年
     * @param pmonth 月
     * @return
     */
    public double cal_price(String pname, int pyear, int pmonth) {
        List<Map<String, Object>> rs = select_price(pname, pyear, pmonth);
        int count = 0;
        double sum = 0;

        if (rs == null) {
            System.out.println("cal_p ERROR !");
        }
        try {
            for (Object row1 : rs) {
                Map row = (Map) row1;
                sum += Double.valueOf((Float) row.get("mprice"));   // 4:mprice 	5:hprice
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        double mean_price;
        if (count == 0 || sum == 0)
            mean_price = 0;
        else
            mean_price = sum / count;
        System.out.println(pname + " : " + pyear + " 年" + pmonth + " 月" + " 共 " + count + " 条记录, 总计\t" + sum + ", 平均值是\t" + mean_price);
        return mean_price;
    }


    public ResultSet select_price(String pname, int pyear, int pmonth, String pclassify) {

        try {
            Connection conn = jdbcTemplate.getDataSource().getConnection();

            String sql = "select * from price_shuichan where pname like '%" + pname + "%' and year(pdate)=? and month(pdate)=? and classify=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, pname);
            ps.setInt(2, pyear);
            ps.setInt(3, pmonth);
            ps.setString(4, pclassify);
            ResultSet rs = ps.executeQuery();
            ps.close();
            conn.close();
            if (rs.next()) {
                System.out.println(rs.getString("mprice"));
            }

            while (rs.next()) {
                countTotal++;
            }
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        }
        System.out.println("Something ERROR !");
        return null;
    }

    public List<Map<String, Object>> select_price(String pname, int pyear, int pmonth) {
        try {
            String year = String.valueOf(pyear);
            String month = String.valueOf(pmonth);
            String sql = "select * from price_shuichan where name like '%" + pname + "%' and year(date)=" + year + " and month(date)=" + month;
            return jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Something ERROR !");
        return null;
    }

    public double[][] showSeries(int fromYear, int toYear, String itemName, String classify) {

        double temp[][] = new double[toYear - fromYear + 1][12];

        for (int j = fromYear; j <= toYear; j++)
            for (int i = 1; i <= 12; i++) {
                if (classify == "")
                    temp[j - fromYear][i - 1] = cal_price(itemName, j, i);
                else
                    temp[j - fromYear][i - 1] = cal_price(itemName, j, i, classify);
            }

        return temp;
    }

    public double[][] getPriceByYear(int fromYear, int toYear, String itemName, String classify) {
        double temp[][] = showSeries(fromYear, toYear, itemName, classify);
        for (int j = 0; j < (toYear - fromYear + 1); j++) {
            System.out.print("temp_" + (j + fromYear) + "=[");
            for (int i = 0; i < 12; i++) {
                System.out.print(String.format("%1$.2f", temp[j][i]) + " ");
            }
            System.out.println("];");
        }

        System.out.println("Total number is : " + countTotal);
        return temp;
    }
/*	public   void main(String[] args) {
		int fromYear=2011;
		int toYear=2017;
		String itemName="梭子蟹";
		String classify="";
		double temp[][] = showSeries(fromYear, toYear, itemName, classify);
		for(int j=0; j<(toYear-fromYear+1); j++){
			System.out.print("temp_" + (j+fromYear) + "=[");
			for(int i=0; i<12;i++){
				System.out.print(String.format("%1$.2f", temp[j][i]) + " ");
			}
			System.out.println("];");
		}
		
		System.out.println("Total number is : " + countTotal);
	}*/
}
