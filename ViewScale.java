import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by leo on 2016/9/10.
 */
public class ViewScale {
    private Activity act;
    private float LayoutDesignWidth=360f;

    private float scale=1f;
    private float density=3f;
    private float dpHeight=100f;
    private float dpWidth=100f;
    private boolean isScale=false;
    public ViewScale(Activity act){
        this.act=act;
        Display display = act.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        density  = act.getResources().getDisplayMetrics().density;
        dpHeight = outMetrics.heightPixels / density;
        dpWidth  = outMetrics.widthPixels / density;
        scale=dpWidth/LayoutDesignWidth;
    }

    public void ReDrawImageView(int id){
        setMargin(id);
        setWH(id);
    }
    public void ReDrawTextView(int id){
        setMargin(id);
        setWH(id);
        setTextSize(id);
    }

    public void setMargin(int id){
        View v=act.findViewById(id);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams )v.getLayoutParams();
        //setMargin(id, lp.leftMargin, lp.topMargin);
        int left=(int)(lp.leftMargin*scale);
        int top=(int)(lp.topMargin*scale);
        lp.setMargins(left, top, lp.rightMargin, lp.bottomMargin);
        v.setLayoutParams(lp);
    }
    public void setMargin(int id,int left,int top){
        View v=act.findViewById(id);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams )v.getLayoutParams();
        left=(int)(left*density*scale);
        top=(int)(top*density*scale);
        lp.setMargins(left,top,lp.rightMargin,lp.bottomMargin);
        v.setLayoutParams(lp);
    }
    public void setWH(int id){
        View v=act.findViewById(id);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams )v.getLayoutParams();
        lp.width=(int)(lp.width*scale);
        lp.height=(int)(lp.height*scale);
        v.requestLayout();
    }
    public void setTextSize(int id){
        TextView v=(TextView)act.findViewById(id);
//        Log.d("TAG", "----TextSize:" + v.getTextSize());
        v.setTextSize(TypedValue.COMPLEX_UNIT_PX, v.getTextSize() * scale);
        int pl=(int)(v.getPaddingLeft()*scale);
        v.setPadding(pl,v.getPaddingTop(),v.getPaddingRight(),v.getPaddingBottom());
    }

    public boolean isScale() {
        return isScale;
    }

    public void setIsScale(boolean isScale) {
        this.isScale = isScale;
    }
}
