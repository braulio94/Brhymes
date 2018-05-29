package brhymes.app.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.StackAdapter;
import brhymes.app.R;
import brhymes.app.model.Author;

/**
 * Created by Braulio Cassule on 5/29/2018.
 **/

public class AuthorsImageStackAdapter extends StackAdapter<Author> {

    public AuthorsImageStackAdapter(Context context) {
        super(context);
    }

    @Override
    public void bindView(Author data, int position, CardStackView.ViewHolder holder) {
        AuthorItemViewHolder authorViewHolder = (AuthorItemViewHolder) holder;
        //authorViewHolder.authorImageView.setImageDrawable();
    }


    @Override
    protected CardStackView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.author_image, parent, false);
        return new AuthorItemViewHolder(view);
    }

    private class AuthorItemViewHolder extends CardStackView.ViewHolder {

        private ImageView authorImageView;

        public AuthorItemViewHolder(View view) {
            super(view);
            authorImageView = itemView.findViewById(R.id.author_image);
        }

        @Override
        public void onItemExpand(boolean b) {
            //Do not expand this shit!
        }
    }
}
