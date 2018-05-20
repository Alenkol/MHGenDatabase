package com.ghstudios.android.features.weapons;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ghstudios.android.mhgendatabase.R;
import com.ghstudios.android.ui.general.DrawSharpness;

public class WeaponBladeDetailFragment extends WeaponDetailFragment{

	private TextView mWeaponSpecialTypeTextView, mWeaponSpecialTextView,
            mWeaponElementTextView,mWeaponElementTypeTextView,mWeaponNoteText,
            mWeaponElement2TextView,mWeaponElement2TypeTextView;
    LinearLayout mWeaponElementLayout,mWeaponElement2Layout,mElementParentLayout;
	private ImageView mWeaponNote1ImageView,
			mWeaponNote2ImageView, mWeaponNote3ImageView;
    private DrawSharpness mWeaponSharpnessDrawnView;
    private View DividerView;
    View NoteContainer;

	public static WeaponBladeDetailFragment newInstance(long weaponId) {
		Bundle args = new Bundle();
		args.putLong(WeaponDetailFragment.ARG_WEAPON_ID, weaponId);
		WeaponBladeDetailFragment f = new WeaponBladeDetailFragment();
		f.setArguments(args);
		return f;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_weapon_blade_detail,
				container, false);

        mWeaponDescription = (TextView) view
                .findViewById(R.id.detail_weapon_description);
		mWeaponTypeTextView = (TextView) view
				.findViewById(R.id.detail_title_bar_text);
		mWeaponAttackTextView = (TextView) view
				.findViewById(R.id.detail_weapon_attack);
		mWeaponElementTextView = (TextView) view
				.findViewById(R.id.detail_weapon_element);
        mWeaponElementTypeTextView = (TextView)view.findViewById(R.id.detail_weapon_element_text);
        mWeaponElement2TextView = (TextView) view
                .findViewById(R.id.detail_weapon_element_2);
        mWeaponElement2TypeTextView = (TextView)view.findViewById(R.id.detail_weapon_element_2_text);
        mWeaponElementLayout = (LinearLayout)view.findViewById(R.id.weapon_detail_element_layout);
        mWeaponElement2Layout = (LinearLayout)view.findViewById(R.id.weapon_detail_element_layout_2);
		mWeaponSharpnessDrawnView = (DrawSharpness) view
				.findViewById(R.id.detail_weapon_blade_sharpness);
		mWeaponRarityTextView = (TextView) view
				.findViewById(R.id.detail_weapon_rarity);
		mWeaponSlotTextView = (TextView) view
				.findViewById(R.id.detail_weapon_slot);
		mWeaponAffinityTextView = (TextView) view
				.findViewById(R.id.detail_weapon_affinity);
		mWeaponDefenseTextView = (TextView) view
				.findViewById(R.id.detail_weapon_defense);
        mWeaponDefenseTextTextView=(TextView) view
                .findViewById(R.id.detail_weapon_defense_text);
		mWeaponCreationTextView = (TextView) view
				.findViewById(R.id.detail_weapon_creation);
		mWeaponUpgradeTextView = (TextView) view
				.findViewById(R.id.detail_weapon_upgrade);
		mWeaponSpecialTypeTextView = (TextView) view
				.findViewById(R.id.detail_weapon_blade_special);
		mWeaponSpecialTextView = (TextView) view
				.findViewById(R.id.detail_weapon_blade_special_value);
        mWeaponNoteText = (TextView)view.findViewById(R.id.detail_weapon_blade_note_text);
		NoteContainer = view.findViewById(R.id.detail_weapon_note_container);
		mWeaponNote1ImageView = (ImageView) view
				.findViewById(R.id.detail_weapon_blade_note1);
		mWeaponNote2ImageView = (ImageView) view
				.findViewById(R.id.detail_weapon_blade_note2);
		mWeaponNote3ImageView = (ImageView) view
				.findViewById(R.id.detail_weapon_blade_note3);

		return view;
	}

	@Override
	protected void updateUI() throws IOException {
		super.updateUI();

		/* Sharpness */
		mWeaponSharpnessDrawnView.init(mWeapon.getSharpness1(),mWeapon.getSharpness2(),mWeapon.getSharpness3());
        // Redraw sharpness after data is loaded
        mWeaponSharpnessDrawnView.invalidate();

        // String notes to use in notes display and song list
        String notes = "";

		// Read a Bitmap from Assets
		AssetManager manager = getActivity().getAssets();
		InputStream open = null;
		Bitmap bitmap = null;
		
		/* Hunting Horn notes */
		if (mWeapon.getWtype().equals("Hunting Horn")) {
			notes = mWeapon.getHornNotes();
			
			try {
                open = manager.open(getNoteImage(notes.charAt(0)));
                bitmap = BitmapFactory.decodeStream(open);
                mWeaponNote1ImageView.setImageBitmap(bitmap);
				
				open = manager.open(getNoteImage(notes.charAt(1)));
				bitmap = BitmapFactory.decodeStream(open);
                mWeaponNote2ImageView.setImageBitmap(bitmap);
				
				open = manager.open(getNoteImage(notes.charAt(2)));
				bitmap = BitmapFactory.decodeStream(open);
                mWeaponNote3ImageView.setImageBitmap(bitmap);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (open != null) {
					open.close();
				}
			}
		}
        else
        {
            mWeaponNoteText.setVisibility(View.GONE);
            NoteContainer.setVisibility(View.GONE);
        }
		
		/* Gunlance */
		if (mWeapon.getWtype().equals("Gunlance")) {
			mWeaponSpecialTypeTextView.setText("Shelling");
			mWeaponSpecialTextView.setText(mWeapon.getShellingType());
		}

        /* Switch Axe */
		else if (mWeapon.getWtype().equals("Switch Axe")) {
			mWeaponSpecialTypeTextView.setText("Phial");
			mWeaponSpecialTextView.setText(mWeapon.getPhial());
		}
        else if(mWeapon.getWtype().equals("Charge Blade")){
            mWeaponSpecialTypeTextView.setText("Phial");
            mWeaponSpecialTextView.setText(mWeapon.getPhial());
        }
        else{
            mWeaponSpecialTextView.setVisibility(View.GONE);
            mWeaponSpecialTypeTextView.setVisibility(View.GONE);
        }


        /* Element */
        if (!mWeapon.getElement().equals(""))
        {
            mWeaponElementTextView.setText(Long.toString(mWeapon.getElementAttack()));
            mWeaponElementTypeTextView.setText(mWeapon.getElement());
        }
        else
        {
            mWeaponElementTextView.setText("0");
            mWeaponElementTypeTextView.setText("None");
        }

		/* Element 2 */
        if (!"".equals(mWeapon.getElement2())) {
            mWeaponElement2TypeTextView.setText(mWeapon.getElement2());
            mWeaponElement2TextView.setText(Long.toString(mWeapon.getElement2Attack()));
        }else{
            mWeaponElement2Layout.setVisibility(View.GONE);
        }

	}

    public static String getNoteImage(char note) {
        String file = "icons_monster_info/";

        switch (note) {
            case 'B':
                return file + "Note.blue.png";
            case 'C':
                return file + "Note.aqua.png";
            case 'G':
                return file + "Note.green.png";
            case 'O':
                return file + "Note.orange.png";
            case 'P':
                return file + "Note.purple.png";
            case 'R':
                return file + "Note.red.png";
            case 'W':
                return file + "Note.white.png";
            case 'Y':
                return file + "Note.yellow.png";
        }
        return "";
    }

}
