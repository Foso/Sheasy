package de.jensklingenberg.sheasy.ui.common;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

/**
 * A diff callback that calculates diffs for lists of {@link BaseDataSourceItem}
 */
public class SourceItemDiffCallback extends DiffUtil.Callback {

    private final List<BaseDataSourceItem> dataSource;
    private final List<BaseDataSourceItem> newItems;

    /**
     * Instantiates a new Source item diff callback.
     *
     * @param dataSource the data source
     * @param newItems   the new items
     */
    public SourceItemDiffCallback(List<BaseDataSourceItem> dataSource, List<BaseDataSourceItem> newItems) {
        this.dataSource = dataSource;
        this.newItems = newItems;
    }

    @Override
    public int getOldListSize() {
        return dataSource.size();
    }

    @Override
    public int getNewListSize() {
        return newItems.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        BaseDataSourceItem oldItem = dataSource.get(oldItemPosition);
        BaseDataSourceItem newItem = newItems.get(newItemPosition);
        return oldItem.areItemsTheSame(newItem);
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        BaseDataSourceItem oldItem = dataSource.get(oldItemPosition);
        BaseDataSourceItem newItem = newItems.get(newItemPosition);
        return oldItem.getChangePayload(newItem);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        BaseDataSourceItem oldItem = dataSource.get(oldItemPosition);
        BaseDataSourceItem newItem = newItems.get(newItemPosition);
        return oldItem.areContentsTheSame(newItem);
    }
}
