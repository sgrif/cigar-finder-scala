package com.seantheprogrammer.cigar_finder_android

import android.content.Context
import android.view.{LayoutInflater, View, ViewGroup}
import android.widget.BaseAdapter

class CigarSearchResultsAdapter(context: Context) extends BaseAdapter {
  private var _results: Option[SearchResults] = None
  private var _listItems: Option[IndexedSeq[ListItem[_]]] = None

  def results = _results match {
    case Some(results) => results
    case None => new SearchResults(IndexedSeq.empty)
  }

  def results_= (results: Option[SearchResults]) = {
    _results = results
    buildListItems
    notifyDataSetChanged
  }

  def listItems = _listItems match {
    case Some(listItems) => listItems
    case None => IndexedSeq.empty
  }

  def listItems_= (listItems: IndexedSeq[ListItem[_]]) = _listItems = Some(listItems)

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

  lazy val layoutInflater = LayoutInflater.from(context)
}
