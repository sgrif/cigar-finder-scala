package com.seantheprogrammer.cigar_finder_android

import android.content.Context
import android.view.{View, ViewGroup}
import android.widget.BaseAdapter
import org.scaloid.common.SystemService

class SearchResultsAdapter(implicit context: Context)
extends BaseAdapter
with SystemService {
  private var _results: SearchResults = new SearchResults(IndexedSeq.empty)
  var listItems: IndexedSeq[ListItem[_]] = IndexedSeq.empty

  def results = _results
  def results_= (results: SearchResults) = {
    _results = results
    buildListItems
    notifyDataSetChanged
  }

  override def getCount = results.size

  override def getItem(index: Int) = listItems(index).data.asInstanceOf[Object]

  override def getView(index: Int, convertView: View, parent: ViewGroup) = convertView match {
    case null => listItems(index).convertView(newView(index, parent))
    case _ => listItems(index).convertView(convertView)
  }

  override def getItemId(index: Int) = listItems(index).id

  override def getItemViewType(index: Int) = listItems(index).viewType

  override def getViewTypeCount = 2

  override def areAllItemsEnabled = false

  override def isEnabled(index: Int) = listItems(index).isEnabled

  private def buildListItems =  {
    listItems = (
      itemsWithHeader("Carried", results.carried)
      ++ itemsWithHeader("Not Carried", results.notCarried)
      ++ itemsWithHeader("No Information", results.noInformation)
    )
  }

  private def itemsWithHeader(header: String, items: IndexedSeq[SearchResult]) = items.isEmpty match {
    case true => IndexedSeq.empty
    case false => new HeaderListItem(header) +: items.map(new SearchResultListItem(_))
  }

  private def newView(index: Int, parent: ViewGroup) = layoutInflater.inflate(listItems(index).layoutId, parent, false)

  lazy val layoutInflater = super.layoutInflater
}
