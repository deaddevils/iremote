package com.iremote.common.Hibernate;

public class BasicScroll {
	  private int pageSize = 10;
	  private int pageSizeMax = 10;
	  private int currentPage = 1;
	  private int pages = 0;
	  private int rows = 0;
	  private int from;
	  private int to;

	  public void compute(int rows)
	  {
	    if (rows < 0)
	      throw new IllegalArgumentException("rows should >=0");
	    this.rows = rows;

	    if (rows != 0) {
	      this.pages = (rows / this.pageSize);
	      if (rows % this.pageSize != 0)
	        this.pages += 1;
	    }
	    else {
	      this.pages = 1;
	    }

	    if (this.currentPage < 1)
	      this.currentPage = 1;
	    else if (this.currentPage > this.pages) {
	      this.currentPage = this.pages;
	    }

	    this.from = ((this.currentPage - 1) * this.pageSize + 1);
	    this.to = (this.currentPage * this.pageSize);
	  }

	  public void setPageSize(int pageSize)
	  {
	    if (pageSize <= 0)
	      throw new IllegalArgumentException("pageSize should be > 0");
	    this.pageSize = pageSize;
	  }

	  public void setPageSizeMax(int pageSizeMax) {
	    if (pageSizeMax <= 0)
	      throw new IllegalArgumentException("pageSize should be > 0");
	    this.pageSizeMax = pageSizeMax;
	  }

	  public void setCurrentPage(int page) {
	    this.currentPage = page;
	  }

	  public int getRows()
	  {
	    return this.rows;
	  }

	  public int getPageSize() {
	    return this.pageSize;
	  }

	  public int getPageSizeMax() {
	    return this.pageSizeMax;
	  }

	  public int getPages() {
	    return this.pages;
	  }

	  public int getCurrentPage() {
	    return this.currentPage;
	  }

	  public int getFrom() {
	    return this.from;
	  }

	  public int getTo()
	  {
	    return this.to;
	  }

	  public String toString() {
	    StringBuffer str = new StringBuffer();
	    str.append("Scroll Page Info: ");
	    str.append("#rows=");
	    str.append(this.rows);
	    str.append("#pages=");
	    str.append(this.pages);
	    str.append("#currentPage=");
	    str.append(this.currentPage);
	    str.append("#pageSize=");
	    str.append(this.pageSize);
	    str.append("#from=");
	    str.append(this.from);
	    str.append("#to=");
	    str.append(this.to);

	    return str.toString();
	  }
}
