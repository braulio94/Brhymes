package brhymes.app.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import brhymes.app.R;
import brhymes.app.cardstackview.StackAdapter;
import brhymes.app.model.Author;

/**
 * Created by Braulio Cassule on 5/29/2018.
 **/

public class AuthorsImageStackAdapter extends StackAdapter<Author> {

    public AuthorsImageStackAdapter(Context context) {
        super(context);
    }

    @Override
    public void bindView(Author data, int position, StackAdapter.ViewHolder holder) {
        AuthorItemViewHolder authorViewHolder = (AuthorItemViewHolder) holder;
        authorViewHolder.authorImageView.setImageDrawable(data.getImageId());
    }


    @Override
    protected StackAdapter.ViewHolder onCreateView(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.author_image, parent, false);
        return new AuthorItemViewHolder(view);
    }

    private class AuthorItemViewHolder extends StackAdapter.ViewHolder {

        private ImageView authorImageView;

        public AuthorItemViewHolder(View view) {
            super(view);
            authorImageView = itemView.findViewById(R.id.author_image);
        }

    }
}
