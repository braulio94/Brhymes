package brhymes.app.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import brhymes.app.R;
import brhymes.app.adapters.LyricLinesAdapter.LyricLinesViewHolder;
import brhymes.app.model.Line;
import static brhymes.app.model.Form.NONE;

/**
 * Created by Braulio Cassule on 6/3/2018.
 **/

public class LyricLinesAdapter extends RecyclerView.Adapter<LyricLinesViewHolder> {

    private static final int VIEW_TYPE_LYRIC_LINE = 0;
    private static final int VIEW_TYPE_LYRIC_FORM = 1;
    private List<Line> mLyricLinesList;
    private Context mContext;

    public LyricLinesAdapter(List<Line> lyricLinesList, Context context) {
        this.mLyricLinesList = lyricLinesList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public LyricLinesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.lyric_line_item, parent, false);
        return new LyricLinesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LyricLinesViewHolder holder, int position) {
        Line mLine = mLyricLinesList.get(position);
        holder.bindView(mLine, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.mNewLineText.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLyricLinesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mLyricLinesList.get(position).getForm() == NONE ? VIEW_TYPE_LYRIC_FORM : VIEW_TYPE_LYRIC_LINE;
    }

    class LyricLinesViewHolder extends RecyclerView.ViewHolder {

        private TextView mLineText;
        private EditText mNewLineText;

        LyricLinesViewHolder(@NonNull View itemView) {
            super(itemView);
            mLineText = itemView.findViewById(R.id.lyric_line_text);
            mNewLineText = itemView.findViewById(R.id.new_lyric_line);
        }

        void bindView(Line line, View.OnClickListener onClickListener){
            mLineText.setText(line.getText());
            mLineText.setOnClickListener(onClickListener);
        }
    }
}
