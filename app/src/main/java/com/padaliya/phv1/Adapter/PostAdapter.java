package com.padaliya.phv1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.MapView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.padaliya.phv1.HazardmapActivity;
import com.padaliya.phv1.Model.Post;
import com.padaliya.phv1.Model.User;
import com.padaliya.phv1.PostActivity;
import com.padaliya.phv1.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Post> mPosts;

    private FirebaseUser firebaseUser;

    public PostAdapter(Context context, List<Post> posts){
        mContext = context;
        mPosts = posts;
    }

    @NonNull
    @Override
    public PostAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_item, parent, false);
        return new PostAdapter.ImageViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final PostAdapter.ImageViewHolder holder, int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final Post post = mPosts.get(position);

        Glide.with(mContext).load(post.getPostimage())
                .into(holder.post_image);

        if (post.getDescription().equals("")){
            holder.description.setVisibility(View.GONE);
        } else {
//            Log.d("Post",post.toString());
            holder.description.setVisibility(View.VISIBLE);
            holder.description.setText(post.getDescription());
        }

        publisherInfo(holder.image_profile, holder.username, holder.publisher, post.getPublisher());

        isUpvoted(post.getPostid(), holder.upvote);
        isdownVoted(post.getPostid(), holder.downvote);

        nUpvotes(holder.upvotes, post.getPostid());
        nDownotes(holder.downvotes, post.getPostid());



        holder.upvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.upvote.getTag().equals("upvote")) {
                    FirebaseDatabase.getInstance().getReference().child("Upvotes").child(post.getPostid())
                            .child(firebaseUser.getUid()).setValue(true);

                    if (holder.downvote.getTag().equals("downvoted")){
                        FirebaseDatabase.getInstance().getReference().child("Downvotes").child(post.getPostid())
                                .child(firebaseUser.getUid()).removeValue();
                    }

                    // implement downvote logics here too
                } else {
                    FirebaseDatabase.getInstance().getReference().child("Upvotes").child(post.getPostid())
                            .child(firebaseUser.getUid()).removeValue();
                }
            }
        });

        holder.downvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.downvote.getTag().equals("downvote")) {
                    FirebaseDatabase.getInstance().getReference().child("Downvotes").child(post.getPostid())
                            .child(firebaseUser.getUid()).setValue(true);

                    if (holder.upvote.getTag().equals("upvoted")){
                        FirebaseDatabase.getInstance().getReference().child("Upvotes").child(post.getPostid())
                                .child(firebaseUser.getUid()).removeValue();
                    }

                    // implement upvote logics here too
                } else {
                    FirebaseDatabase.getInstance().getReference().child("Downvotes").child(post.getPostid())
                            .child(firebaseUser.getUid()).removeValue();
                }
            }
        });

        holder.location_pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toBeSent = new Intent(mContext, HazardmapActivity.class);
                toBeSent.putExtra("post",post);
                mContext.startActivity(toBeSent);

            }
        });
    }



    @Override
    public int getItemCount() {
        Log.d("Post",mPosts.size()+"");

        return mPosts.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageView image_profile, post_image, upvote,downvote,location_pin;
        public TextView username, upvotes,downvotes, publisher, description;

        public ImageViewHolder(View itemView) {
            super(itemView);

            image_profile = itemView.findViewById(R.id.image_profile);
            username = itemView.findViewById(R.id.username);
            post_image = itemView.findViewById(R.id.post_image);
            upvote = itemView.findViewById(R.id.upvote);
            downvote = itemView.findViewById(R.id.downvote);
            upvotes = itemView.findViewById(R.id.upvotes);
            downvotes = itemView.findViewById(R.id.downvotes);
            publisher = itemView.findViewById(R.id.publisher);
            description = itemView.findViewById(R.id.description);
            location_pin = itemView.findViewById(R.id.locationpin);


        }
    }

    private void publisherInfo(final ImageView image_profile, final TextView username, final TextView publisher, final String userid){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(userid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                Log.d("Post",user.toString());

                Glide.with(mContext).load(user.getimageurl()).into(image_profile);
                username.setText(user.getUsername());
                publisher.setText(user.getUsername());


            }





            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void isUpvoted(String postid, final ImageView imageView){

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Upvotes")
                .child(postid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.child(firebaseUser.getUid()).exists()){
                    imageView.setImageResource(R.drawable.ic_upvoted);
                    imageView.setTag("upvoted");
                }else  {
                    imageView.setImageResource(R.drawable.ic_upvote);
                    imageView.setTag("upvote");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void isdownVoted(String postid, final ImageView imageView){

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Downvotes")
                .child(postid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.child(firebaseUser.getUid()).exists()){
                    imageView.setImageResource(R.drawable.ic_downvoted);
                    imageView.setTag("downvoted");
                }else  {
                    imageView.setImageResource(R.drawable.ic_downvote);
                    imageView.setTag("downvote");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void nUpvotes(final TextView upvoteCounter, String postId){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Upvotes").child(postId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                upvoteCounter.setText(dataSnapshot.getChildrenCount()+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void nDownotes(final TextView downVoteCounter, String postId){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Downvotes").child(postId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                downVoteCounter.setText(dataSnapshot.getChildrenCount()+"");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
