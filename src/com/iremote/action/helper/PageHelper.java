package com.iremote.action.helper;

import java.io.Serializable;
import java.util.List;

public class PageHelper<T> implements Serializable {

    private static final long serialVersionUID = 4542617637761955078L;

    /**
     * currentPage 
     */
    private int currentPage = 1;
    /**
     * pageSize 
     */
    private int pageSize = 20;
    /**
     * pageTotal 
     */
    private int pageTotal;
    /**
     * recordTotal 
     */
    private int recordTotal = 0;
    /**
     * previousPage 
     */
    private int previousPage;
    /**
     * nextPage
     */
    private int nextPage;
    /**
     * firstPage
     */
    private int firstPage = 1;
    /**
     * lastPage
     */
    private int lastPage;
    /**
     * content
     */
    private List<T> content;

 

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }


    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
        otherAttr();
    }


    public void setContent(List<T> content) {
        this.content = content;
    }


    public void otherAttr() {
        this.pageTotal = this.recordTotal % this.pageSize > 0 ? this.recordTotal / this.pageSize + 1 : this.recordTotal / this.pageSize;
        this.firstPage = 1;
        this.lastPage = this.pageTotal;
        if (this.currentPage > 1) {
            this.previousPage = this.currentPage - 1;
        } else {
            this.previousPage = this.firstPage;
        }
        if (this.currentPage < this.lastPage) {
            this.nextPage = this.currentPage + 1;
        } else {
            this.nextPage = this.lastPage;
        }
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public int getRecordTotal() {
        return recordTotal;
    }

    public int getPreviousPage() {
        return previousPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public List<T> getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Pager [currentPage=" + currentPage + ", pageSize=" + pageSize
                + ", pageTotal=" + pageTotal + ", recordTotal=" + recordTotal
                + ", previousPage=" + previousPage + ", nextPage=" + nextPage
                + ", firstPage=" + firstPage + ", lastPage=" + lastPage
                + ", content=" + content + "]";
    }

}
