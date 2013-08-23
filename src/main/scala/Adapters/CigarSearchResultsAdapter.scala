package com.seantheprogrammer.cigar_finder_android

import android.content.Context
import android.view.{LayoutInflater, View, ViewGroup}
import android.widget.BaseAdapter

class CigarSearchResultsAdapter(context: Context) extends BaseAdapter {
  private var results: Option[CigarSearchResults] = None
  private var listItems: Option[IndexedSeq[ListItem[_]]] = None

  def setResults(results: Option[CigarSearchResults]) = {
    this.results = results
    buildListItems
    notifyDataSetChanged
  }

  override def getCount = results match {
    case Some(results) => results.size
    case None => 0
  }

  override def getItem(index: Int) = listItems match {
    case Some(items) => items(index).data.asInstanceOf[Object]
    case None => None
  }

  override def getView(index: Int, convertView: View, parent: ViewGroup): View = listItems match {
    case None => null
    case Some(items) => convertView match {
      case null => items(index).convertView(newView(index, parent))
      case _ => items(index).convertView(convertView)
    }
  }

  override def getItemId(index: Int) = index

  override def getItemViewType(index: Int) = listItems.get(index).viewType

  override def getViewTypeCount = 2

  override def areAllItemsEnabled = false

  override def isEnabled(index: Int) = listItems.get(index).isEnabled

  private def buildListItems = results match {
    case None => listItems = None
    case Some(results) => {
      listItems = Some(
        itemsWithHeader("Carried", results.carried)
        ++ itemsWithHeader("Not Carried", results.notCarried)
        ++ itemsWithHeader("No Information", results.noInformation)
      )
    }
  }

  private def itemsWithHeader(header: String, items: IndexedSeq[CigarSearchResult]) = items.isEmpty match {
    case true => IndexedSeq.empty
    case false => new HeaderListItem(header) +: items.map(new CigarSearchResultListItem(_))
  }

  private def newView(index: Int, parent: ViewGroup) = layoutInflater.inflate(listItems.get(index).layoutId, parent, false)

  lazy val layoutInflater = LayoutInflater.from(context)
}
