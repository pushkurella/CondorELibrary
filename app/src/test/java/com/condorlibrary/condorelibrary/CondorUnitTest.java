package com.condorlibrary.condorelibrary;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowIntent;
import org.robolectric.shadows.ShadowToast;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,sdk = Build.VERSION_CODES.LOLLIPOP)
public class CondorUnitTest {
    RegisterActivity registerActivity;
    LoginActivity loginActivity;
    UpdateItemActivity updateItemActivity;
    Context appContext;
    ShadowApplication shadowApplication;
    CreateItemActivity createItemActivity;
    ViewItemActivity viewItemActivity;
    UpdateAccountActivity updateAccountActivity;
    MainActivity mainActivity;
    ViewActivity viewActivity;
    ShadowActivity shadowActivity;

    @Before
    public void setUp(){

        loginActivity= Robolectric.setupActivity(LoginActivity.class);
        viewItemActivity=Robolectric.setupActivity(ViewItemActivity.class);
        updateAccountActivity=Robolectric.setupActivity(UpdateAccountActivity.class);
       // createItemActivity=Robolectric.setupActivity(CreateItemActivity.class);
        updateItemActivity=Robolectric.setupActivity(UpdateItemActivity.class);
        appContext= RuntimeEnvironment.application.getApplicationContext();
        shadowApplication= shadowOf(RuntimeEnvironment.application);
        //mainActivity = new MainActivity();
        mainActivity=Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void CheckToastMessageForIncorrectLogin(){

        Button loginBtn=(Button) loginActivity.findViewById(R.id.dummy_button);
        EditText userEmail=(EditText)loginActivity.findViewById(R.id.editTextUserEmail);
        EditText userPassword=(EditText) loginActivity.findViewById(R.id.editTextPassword);
        userEmail.setText("pushpak.k.victory@gmail.com" );
        userPassword.setText("123456" );
        loginBtn.performClick();
        assertEquals(ShadowToast.getTextOfLatestToast(),"Please register to use the service");
    }

    @Test
    public void CheckToastIfItemIsNotUpdated(){
        EditText eTItemName=updateItemActivity.findViewById(R.id.editTextItemName);
        EditText eTItemCost=updateItemActivity.findViewById(R.id.editTextCost);
        eTItemName.setText("Android dev" );
        eTItemCost.setText("40" );
        Button updateBtn=updateItemActivity.findViewById(R.id.buttonUpdate);
        updateBtn.performClick();
        assertEquals(ShadowToast.getTextOfLatestToast(),"Item not found!!!");
    }

    @Test
    public void CheckSharingIntentIsInvoked(){

        Button btnShare=createItemActivity.findViewById(R.id.btnShare);
        btnShare.performClick();
        ShadowActivity shadowActivity = shadowOf(createItemActivity);
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertThat(shadowIntent.getClass().getName(),
                equalTo("goo"));
    }

    @Test
    public void CheckIsAccountDetailsUpdated(){
        EditText editForename=updateAccountActivity.findViewById(R.id.editTextFname);
        EditText editSurname=updateAccountActivity.findViewById(R.id.editTextSname);
        EditText editEmail=updateAccountActivity.findViewById(R.id.editTextEmail);
        editForename.setText("Lanister" );
        editSurname.setText("Tyrion" );
        editEmail.setText("tyrion.lanister@conestogac.on.ca" );
        Button btnUpdate=updateAccountActivity.findViewById(R.id.btn_update);
        btnUpdate.performClick();
        assertEquals(ShadowToast.getTextOfLatestToast(),"Incorrect email!!!");
    }
    @Test
    public void CheckIfStudentAlreadyExistsInDatabase(){

        LibraryDatabase.Library libraryRep = new LibraryDatabase.Library(appContext);
        String email= "tyrion.lanister@conestogac.on.ca";
        String password ="Password123";
        boolean isUpdated = libraryRep.CheckStudentExists(email, password);
        assertEquals(false, isUpdated );
    }

    @Test
    public void CheckIfStudentDetailsAreInserted(){

        LibraryDatabase.Library libraryRep = new LibraryDatabase.Library(appContext);
        String forename= "Lanister";
        String surname="Tyrion";
        String email = "tyrion.lanister@conestogac.on.ca";
        String password = "Password123";
        long transactionId= libraryRep.InsertStudentDetails(forename,surname ,email ,password );
        boolean areDetailsInserted= transactionId >0 ? true:false;
        assertEquals(true,areDetailsInserted);
    }
    @Test
    public void CheckIfIntentIsFired(){
        Button btnCreate=mainActivity.findViewById(R.id.btnCreateItem);
        btnCreate.performClick();
        ShadowActivity shadowActivity = shadowOf(mainActivity);
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertThat(shadowIntent.getIntentClass().getName().toString(), equalTo("com.condorlibrary.condorelibrary.CreateItemActivity"));
    }
    @Test
    public void CheckItemAddedToDB(){

        createItemActivity=Robolectric.setupActivity(CreateItemActivity.class);
        EditText etItemName=createItemActivity.findViewById(R.id.eTItemName);
        EditText etItemCost=createItemActivity.findViewById(R.id.eTItemCost);
        Button submitBtn=createItemActivity.findViewById(R.id.btnSubmit);
        etItemName.setText("Android A to Z" );
        etItemCost.setText("340" );
        submitBtn.performClick();
        assertEquals(ShadowToast.getTextOfLatestToast(),"Item details added to database");

    }

    @Test
    public void CheckIfItemIsDeletedFromDatabase(){
        viewActivity=Robolectric.setupActivity(ViewActivity.class);
        LibraryDatabase.Library libraryRep=new LibraryDatabase.Library(appContext);
        String itemName="Not present item";
        long transactionId= libraryRep.DeleteItem(itemName);
        boolean isItemDeleted= transactionId >0 ? true:false;
        assertEquals(false,isItemDeleted);

    }
    @Test
    public void CheckIfItemDetailsTransferredToNextIntent(){

        viewActivity=Robolectric.setupActivity(ViewActivity.class);
        ListView lvItems=viewActivity.findViewById(R.id.listItems);
        lvItems.performClick();
        Intent intent = new Intent(shadowApplication.getApplicationContext(), ViewItemActivity.class);
        intent.putExtra("extra", "checking extras");
        //String xyz = Robolectric.buildActivity(ViewItemActivity.class).getIntent().getStringExtra("extra");
        ViewActivity activity=Robolectric.buildActivity(ViewActivity.class, intent).create().get();
        assertEquals(activity.getIntent().getStringExtra("foo").toString(),"checking extras");
    }
//    @Test
//    public void CheckToastMessageWhetherDataAddedToDB(){
//        Robolectric.flushForegroundThreadScheduler();
//        registerActivity=Robolectric.setupActivity(RegisterActivity.class);
//        AutoCompleteTextView forenameTV=(AutoCompleteTextView) registerActivity.findViewById(R.id.forename);
//        AutoCompleteTextView surnameTV=(AutoCompleteTextView) registerActivity.findViewById(R.id.surname);
//        AutoCompleteTextView email=(AutoCompleteTextView) registerActivity.findViewById(R.id.email);
//        AutoCompleteTextView password=(AutoCompleteTextView) registerActivity.findViewById(R.id.password);
//        AutoCompleteTextView repeatPassword=(AutoCompleteTextView) registerActivity.findViewById(R.id.repeatPassword);
//        Button registerBtn=(Button) registerActivity.findViewById(R.id.email_sign_in_button);
//
//        forenameTV.setText("Lanister");
//        surnameTV.setText("Tyrion");
//        email.setText("pushpak.k.victory@gmail.com" );
//        password.setText("123456" );
//        repeatPassword.setText("123456");
//        registerBtn.performClick();
//        assertEquals(ShadowToast.getTextOfLatestToast(),"Please register to use the services");
//    }
//    @Test
//    public void CheckIfItemIsDeletedFromDatabase(){
//        viewActivity=Robolectric.setupActivity(ViewActivity.class);
//        ListView lvItems=viewActivity.findViewById(R.id.listItems);
//        lvItems.performLongClick();
//        assertEquals(ShadowToast.getTextOfLatestToast(),"Item details added to database");
//
//    }

}

