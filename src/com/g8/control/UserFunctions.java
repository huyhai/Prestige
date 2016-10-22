/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package com.g8.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.g8.adapter.BannerAdapter;
import com.g8.common.ConstantValue;
import com.g8.common.Utilities;
import com.g8.service.JSONCallBack;
import com.g8.service.JSONMethod;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class UserFunctions {
	private static UserFunctions userFunctions;
	// private JSONParser jsonParser;
	private static String message;
	private Boolean isShowMessage = false;

	// private static String loginURL = "http://digipay.vn/digipayWS_New/";
	// private static String registerURL = "http://digipay.vn/digipayWS_New/";

	public static UserFunctions getInstance() {
		if (userFunctions == null)
			userFunctions = new UserFunctions();
		return userFunctions;
	}

	public void sendMessage(Context cont, String Action, Boolean isSuccess) {
		Intent intent = new Intent(Action);
		intent.putExtra(ConstantValue.IS_SUCCESS, isSuccess);
		cont.sendBroadcast(intent);
	}

	// constructor
	// public UserFunctions() {
	// jsonParser = new JSONParser();
	// }

	public String getMessage() {
		if (message == null) {
			message = "Not Message!";
		}
		return message;
	}

	public static String ADDDEVICE = "adddive";

	public void add(Context cont, List<NameValuePair> JSONObjectParams,
			Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, adddive1,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack adddive1 = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				// String message = Utilities.getDataString(resultJson,
				// "message");
				// if (null != result) {
				// if ((Boolean) resultJson.get("is_success")) {
				// sendMessage(activity, ADDDEVICE, true);
				// } else {
				// sendMessage(activity, ADDDEVICE, false);
				// }
				//
				// } else {
				// sendMessage(activity, ADDDEVICE, false);
				// }
			} catch (Exception e) {
				// TODO: handle exception
				// sendMessage(activity, ADDDEVICE, false);
			}
			// Log.e("JSON", json);
		}
	};

	public static String GETGRO = "getgro";

	public static String GETRROOM = "downsoftpin";
	public ArrayList<RoomModel> listSiriel;
	private RoomModel roomModel;

	public ArrayList<RoomModel> getlistRoom() {
		if (null == listSiriel) {
			listSiriel = new ArrayList<RoomModel>();
		}
		return listSiriel;
	}

	public void getRoom(Activity cont, List<NameValuePair> JSONObjectParams,
			Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, downsoftpin,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack downsoftpin = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						listSiriel = new ArrayList<RoomModel>();
						JSONArray listJson;
						listJson = resultJson.getJSONArray("list_data");
						for (int i = 0; i < listJson.length(); i++) {
							JSONObject jSonObFriend = new JSONObject();
							jSonObFriend = listJson.getJSONObject(i);
							roomModel = new RoomModel();
							roomModel.setData(jSonObFriend);
							listSiriel.add(roomModel);
						}
						sendMessage(activity, GETRROOM, true);
					} else {
						sendMessage(activity, GETRROOM, false);
					}

				} else {
					sendMessage(activity, GETRROOM, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, GETRROOM, false);
			}

		}
	};
	public static String TOPUPGAME = "tpgame";
	public static String amount;
	public String tgAcount;

	public void callTopupGame(Activity cont,
			List<NameValuePair> JSONObjectParams, Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, tpgame,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack tpgame = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						amount = Utilities.getDataString(resultJson, "amount");
						tgAcount = Utilities.getDataString(resultJson,
								"targetAccount");
						sendMessage(activity, TOPUPGAME, true);
					} else {
						sendMessage(activity, TOPUPGAME, false);
					}

				} else {
					sendMessage(activity, TOPUPGAME, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, TOPUPGAME, false);
			}

		}
	};
	public static String TOPUPMOBILE = "tpmobile";
	public String amountMobile;
	public String tgAcountMobile;
	public boolean isAdap = false;

	public void callTopupMobile(Activity cont,
			List<NameValuePair> JSONObjectParams, Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, tpmobile,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack tpmobile = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						amountMobile = Utilities.getDataString(resultJson,
								"amount");
						tgAcountMobile = Utilities.getDataString(resultJson,
								"phonenumber");
						sendMessage(activity, TOPUPMOBILE, true);
					} else {
						sendMessage(activity, TOPUPMOBILE, false);
					}

				} else {
					sendMessage(activity, TOPUPMOBILE, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, TOPUPMOBILE, false);
			}

		}
	};
	public static String LOGINUSER = "loginuser";
	public UserModel userModel;

	public UserModel getUserModel() {
		if (null == userModel) {
			userModel = new UserModel();
		}
		return userModel;
	}

	// public static String amount;
	// public String tgAcount;
	public void callLoginUser(Activity cont,
			List<NameValuePair> JSONObjectParams, Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, loginuser,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack loginuser = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						userModel = new UserModel();
						userModel
								.setData(resultJson.getJSONObject("user_info"));
						sendMessage(activity, LOGINUSER, true);
					} else {
						sendMessage(activity, LOGINUSER, false);
					}

				} else {
					sendMessage(activity, LOGINUSER, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, LOGINUSER, false);
			}

		}
	};
	public static String BOOKGRO = "getNotifi";

	// public static String amount;
	// public String tgAcount;
	public void bookGRP(Activity cont, List<NameValuePair> JSONObjectParams,
			Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, getNotifi,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack getNotifi = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						sendMessage(activity, BOOKGRO, true);
					} else {
						sendMessage(activity, BOOKGRO, false);
					}

				} else {
					sendMessage(activity, BOOKGRO, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, BOOKGRO, false);
			}

		}
	};
	public static String CONTACT = "contact";
	private ContactModel contactModel;

	public ContactModel getContactModel() {
		if (null == contactModel) {
			contactModel = new ContactModel();
		}
		return contactModel;
	}

	public void callContact(Activity cont,
			List<NameValuePair> JSONObjectParams, Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, contact,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack contact = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						contactModel = new ContactModel();
						contactModel.setData(resultJson
								.getJSONObject("list_data"));
						sendMessage(activity, CONTACT, true);
					} else {
						sendMessage(activity, CONTACT, false);
					}

				} else {
					sendMessage(activity, CONTACT, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, CONTACT, false);
			}

		}
	};
	public static String LISTADS = "listAds";
	public AdsModel adsModel;
	private ArrayList<AdsModel> listAdsModel;

	public AdsModel getAdsModel() {
		if (null == adsModel) {
			adsModel = new AdsModel();
		}
		return adsModel;
	}

	public ArrayList<AdsModel> getListAdsModel() {
		if (null == listAdsModel) {
			listAdsModel = new ArrayList<AdsModel>();
		}
		return listAdsModel;
	}

	public void getListAds(Activity cont, List<NameValuePair> JSONObjectParams,
			Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, listAds,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack listAds = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						listAdsModel = new ArrayList<AdsModel>();
						JSONArray listJson;
						listJson = resultJson.getJSONArray("list_data");
						for (int i = 0; i < listJson.length(); i++) {
							JSONObject jSonObFriend = new JSONObject();
							jSonObFriend = listJson.getJSONObject(i);
							adsModel = new AdsModel();
							adsModel.setData(jSonObFriend);
							listAdsModel.add(adsModel);
						}

						sendMessage(activity, LISTADS, true);
					} else {
						sendMessage(activity, LISTADS, false);
					}

				} else {
					sendMessage(activity, LISTADS, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, LISTADS, false);
			}

		}
	};
	public GROModel groModel;
	public static ArrayList<GROModel> listImagesModel;

	// public AdsModel getAdsModel() {
	// if (null == adsModel) {
	// adsModel = new AdsModel();
	// }
	// return adsModel;
	// }

	public ArrayList<GROModel> getListGRO() {
		if (null == listImagesModel) {
			listImagesModel = new ArrayList<GROModel>();
		}
		return listImagesModel;
	}

//	private Activity _act;
	public void getGRO(Context cont, List<NameValuePair> JSONObjectParams,
			Boolean _isShowProgressBar) {
//		_act=cont;
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, listImages,
				_isShowProgressBar);
		// method.execute();
	}
	
	JSONCallBack listImages = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						listImagesModel = new ArrayList<GROModel>();
						JSONArray listJson;
						listJson = resultJson.getJSONArray("list_data");
						for (int i = 0; i < listJson.length(); i++) {
							JSONObject jSonObFriend = new JSONObject();
							jSonObFriend = listJson.getJSONObject(i);
							groModel = new GROModel();
							groModel.setData(jSonObFriend);
							listImagesModel.add(groModel);
						}

						sendMessage(activity, GETGRO, true);
//						BannerAdapter	band = new BannerAdapter(_act, listImagesModel);
//						band.setItemList(listImagesModel);
//						band.notifyDataSetChanged();
					} else {
//						listImagesModel.clear();
						sendMessage(activity, GETGRO, false);
					}

				} else {
					sendMessage(activity, GETGRO, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, GETGRO, false);
			}

		}
	};
	public static String GETGRO1 = "getgro1";
	public void getGRO1(Context cont, List<NameValuePair> JSONObjectParams,
			Boolean _isShowProgressBar) {
//		_act=cont;
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, listImages1,
				_isShowProgressBar);
		// method.execute();
	}
	
	JSONCallBack listImages1 = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						listImagesModel = new ArrayList<GROModel>();
						JSONArray listJson;
						listJson = resultJson.getJSONArray("list_data");
						for (int i = 0; i < listJson.length(); i++) {
							JSONObject jSonObFriend = new JSONObject();
							jSonObFriend = listJson.getJSONObject(i);
							groModel = new GROModel();
							groModel.setData(jSonObFriend);
							listImagesModel.add(groModel);
						}

						sendMessage(activity, GETGRO1, true);
//						BannerAdapter	band = new BannerAdapter(_act, listImagesModel);
//						band.setItemList(listImagesModel);
//						band.notifyDataSetChanged();
					} else {
//						listImagesModel.clear();
						sendMessage(activity, GETGRO1, false);
					}

				} else {
					sendMessage(activity, GETGRO1, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, GETGRO1, false);
			}

		}
	};
	public static String GETALLGRO = "getallgro1";
	public AllGROModel allgroModel;
	public static ArrayList<AllGROModel> listAllImagesModel;
	public ArrayList<AllGROModel> getListAllGRO() {
		if (null == listAllImagesModel) {
			listAllImagesModel = new ArrayList<AllGROModel>();
		}
		return listAllImagesModel;
	}
	public void getAllGRO(Context cont, List<NameValuePair> JSONObjectParams,
			Boolean _isShowProgressBar) {
		message = "Not Message!";
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, allGro,
				_isShowProgressBar);
	}
	
	JSONCallBack allGro = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						listAllImagesModel = new ArrayList<AllGROModel>();
						JSONArray listJson;
						listJson = resultJson.getJSONArray("list_data");
						for (int i = 0; i < listJson.length(); i++) {
							JSONObject jSonObFriend = new JSONObject();
							jSonObFriend = listJson.getJSONObject(i);
							allgroModel = new AllGROModel();
							allgroModel.setData(jSonObFriend);
							listAllImagesModel.add(allgroModel);
						}

						sendMessage(activity, GETALLGRO, true);
//						BannerAdapter	band = new BannerAdapter(_act, listImagesModel);
//						band.setItemList(listImagesModel);
//						band.notifyDataSetChanged();
					} else {
//						listImagesModel.clear();
						sendMessage(activity, GETALLGRO, false);
					}

				} else {
					sendMessage(activity, GETALLGRO, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, GETALLGRO, false);
			}

		}
	};
	public static String GETGROBOOKED = "getallgfdggro1";
	public AllGROModel allgroModel1;
	public static ArrayList<AllGROModel> listGRPBooked;
	public ArrayList<AllGROModel> getlistGRPBooked() {
		if (null == listGRPBooked) {
			listGRPBooked = new ArrayList<AllGROModel>();
		}
		return listGRPBooked;
	}
	public void getBookedGRO(Context cont, List<NameValuePair> JSONObjectParams,
			Boolean _isShowProgressBar) {
		message = "Not Message!";
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, allGroa,
				_isShowProgressBar);
	}
	
	JSONCallBack allGroa = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						listGRPBooked = new ArrayList<AllGROModel>();
						JSONArray listJson;
						listJson = resultJson.getJSONArray("list_data");
						for (int i = 0; i < listJson.length(); i++) {
							JSONObject jSonObFriend = new JSONObject();
							jSonObFriend = listJson.getJSONObject(i);
							allgroModel1 = new AllGROModel();
							allgroModel1.setData(jSonObFriend);
							listGRPBooked.add(allgroModel1);
						}

						sendMessage(activity, GETGROBOOKED, true);
//						BannerAdapter	band = new BannerAdapter(_act, listImagesModel);
//						band.setItemList(listImagesModel);
//						band.notifyDataSetChanged();
					} else {
//						listImagesModel.clear();
						sendMessage(activity, GETGROBOOKED, false);
					}

				} else {
					sendMessage(activity, GETGROBOOKED, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, GETGROBOOKED, false);
			}

		}
	};

	public static String BOOKINTERNET = "bookInternet";

	public void callBookInternet(Activity cont,
			List<NameValuePair> JSONObjectParams, Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		JSONMethod method = new JSONMethod(cont, JSONObjectParams,
				bookInternet, _isShowProgressBar);
		// method.execute();
	}

	JSONCallBack bookInternet = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						sendMessage(activity, BOOKINTERNET, true);
					} else {
						sendMessage(activity, BOOKINTERNET, false);
					}

				} else {
					sendMessage(activity, BOOKINTERNET, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, BOOKINTERNET, false);
			}

		}
	};

	// public static String ADDDEVICE = "adddive";

	public void addDV(Activity cont, List<NameValuePair> JSONObjectParams,
			Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, adddive,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack adddive = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					// if ((Boolean) resultJson.get("is_success")) {
					// sendMessage(activity, ADDDEVICE, true);
					// } else {
					// sendMessage(activity, ADDDEVICE, false);
					// }

				} else {
					// sendMessage(activity, ADDDEVICE, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				// sendMessage(activity, ADDDEVICE, false);
			}

		}
	};
	public static String UPDATEPOINT = "upPoint";

	public void callUpdatePoint(Activity cont,
			List<NameValuePair> JSONObjectParams, Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, upPoint,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack upPoint = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					// isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						sendMessage(activity, UPDATEPOINT, true);
					} else {
						sendMessage(activity, UPDATEPOINT, false);
					}

				} else {
					sendMessage(activity, UPDATEPOINT, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, UPDATEPOINT, false);
			}

		}
	};
	public static String BOOKREWARDS = "bookRewards";

	public void callBookRewards(Activity cont,
			List<NameValuePair> JSONObjectParams, Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, bookRewards,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack bookRewards = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						sendMessage(activity, BOOKREWARDS, true);
					} else {
						sendMessage(activity, BOOKREWARDS, false);
					}

				} else {
					sendMessage(activity, BOOKREWARDS, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, BOOKREWARDS, false);
			}

		}
	};
	public static String SIGNUPUSER = "signupuser";

	public void callSignup(Activity cont, List<NameValuePair> JSONObjectParams,
			Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, signupuser,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack signupuser = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						userModel = new UserModel();
						userModel
								.setData(resultJson.getJSONObject("user_info"));
						sendMessage(activity, SIGNUPUSER, true);
					} else {
						sendMessage(activity, SIGNUPUSER, false);
					}

				} else {
					sendMessage(activity, SIGNUPUSER, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, SIGNUPUSER, false);
			}

		}
	};

	public static String LOGMOBILE = "logmobile";

	// public LogModel logModel;
	// private ArrayList<LogModel> listLogModel;
	//
	// public LogModel getLogModel() {
	// if (null == logModel) {
	// logModel = new LogModel();
	// }
	// return logModel;
	// }
	//
	// public ArrayList<LogModel> getListLogMobile() {
	// if (null == listLogModel) {
	// listLogModel = new ArrayList<LogModel>();
	// }
	// return listLogModel;
	// }

	// public static String amount;
	// public String tgAcount;
	public void callLogMobile(Activity cont,
			List<NameValuePair> JSONObjectParams, Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, logmobile,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack logmobile = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {

						// listLogModel = new ArrayList<LogModel>();
						// JSONArray listJson;
						// listJson = resultJson.getJSONArray("log_mobile");
						// for (int i = 0; i < listJson.length(); i++) {
						// JSONObject jSonObFriend = new JSONObject();
						// jSonObFriend = listJson.getJSONObject(i);
						// logModel = new LogModel();
						// logModel.setData(jSonObFriend);
						// listLogModel.add(logModel);
						// }

						sendMessage(activity, LOGMOBILE, true);
					} else {
						sendMessage(activity, LOGMOBILE, false);
					}

				} else {
					sendMessage(activity, LOGMOBILE, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, LOGMOBILE, false);
			}

		}
	};

	public static String LOGGAME = "loggame";
	public LogGameModel logGameModel;
	private ArrayList<LogGameModel> listLogGameModel;

	// public LogGameModel getLogModel() {
	// if (null == logModel) {
	// logModel = new LogLogGameModelModel();
	// }
	// return logModel;
	// }
	public ArrayList<LogGameModel> getListLogGame() {
		if (null == listLogGameModel) {
			listLogGameModel = new ArrayList<LogGameModel>();
		}
		return listLogGameModel;
	}

	// public static String amount;
	// public String tgAcount;
	public void callLogGame(Activity cont,
			List<NameValuePair> JSONObjectParams, Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, loggame,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack loggame = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {

						listLogGameModel = new ArrayList<LogGameModel>();
						JSONArray listJson;
						listJson = resultJson.getJSONArray("log_game");
						for (int i = 0; i < listJson.length(); i++) {
							JSONObject jSonObFriend = new JSONObject();
							jSonObFriend = listJson.getJSONObject(i);
							logGameModel = new LogGameModel();
							logGameModel.setData(jSonObFriend);
							listLogGameModel.add(logGameModel);
						}

						sendMessage(activity, LOGGAME, true);
					} else {
						sendMessage(activity, LOGGAME, false);
					}

				} else {
					sendMessage(activity, LOGGAME, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, LOGGAME, false);
			}

		}
	};

	public static String PERCENT = "percent";
	private static ArrayList<PercentServiceModel> listPercent;
	public PercentServiceModel percentServiceModel;

	public ArrayList<PercentServiceModel> getListPercent() {
		if (null == listPercent) {
			listPercent = new ArrayList<PercentServiceModel>();
		}
		return listPercent;
	}

	public void callPercent(Activity cont,
			List<NameValuePair> JSONObjectParams, Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, percent,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack percent = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {

						listPercent = new ArrayList<PercentServiceModel>();
						JSONArray listJson;
						listJson = resultJson.getJSONArray("listpercent");
						for (int i = 0; i < listJson.length(); i++) {
							JSONObject jSonObFriend = new JSONObject();
							jSonObFriend = listJson.getJSONObject(i);
							percentServiceModel = new PercentServiceModel();
							percentServiceModel.setData(jSonObFriend);
							listPercent.add(percentServiceModel);
						}

						sendMessage(activity, PERCENT, true);
					} else {
						sendMessage(activity, PERCENT, false);
					}

				} else {
					sendMessage(activity, PERCENT, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, PERCENT, false);
			}

		}
	};

	public static String GETLISTREGULAR = "getlistregular";
	private ArrayList<RegularModel> listRegular = new ArrayList<RegularModel>();
	private RegularModel regularModel;

	public ArrayList<RegularModel> getlistRegular() {
		if (null == listRegular) {
			listRegular = new ArrayList<RegularModel>();
		}
		return listRegular;
	}

	public void callGetlistRegular(Activity cont,
			List<NameValuePair> JSONObjectParams, Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		@SuppressWarnings("unused")
		JSONMethod method = new JSONMethod(cont, JSONObjectParams,
				getlistregular, _isShowProgressBar);
		// method.execute();
	}

	JSONCallBack getlistregular = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						JSONArray list;
						list = resultJson.getJSONArray("list_data");
						for (int i = 0; i < list.length(); i++) {
							JSONObject jSonOb = new JSONObject();
							jSonOb = list.getJSONObject(i);
							regularModel = new RegularModel();
							regularModel.setData(jSonOb);
							listRegular.add(regularModel);
						}
						sendMessage(activity, GETLISTREGULAR, true);
					} else {
						sendMessage(activity, GETLISTREGULAR, false);
					}

				} else {
					sendMessage(activity, GETLISTREGULAR, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, GETLISTREGULAR, false);
			}

		}
	};
	public ArrayList<String> listPhone = new ArrayList<String>();

	// public ArrayList<String> listName =new ArrayList<String>();
	public class RegularModel implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String fullName;
		private String phoneNumber;
		private String rechargeTime;
		private String createDate;
		private String note;
		private String status;

		public void setData(JSONObject jSonInfo) throws JSONException {
			this.setFullName(Utilities.getDataString(jSonInfo, "fullName"));
			// listName.add(fullName);
			this.setPhoneNumber(Utilities
					.getDataString(jSonInfo, "phoneNumber"));
			listPhone.add(phoneNumber);
			this.setRechargeTime(Utilities.getDataString(jSonInfo,
					"rechargeTime"));
			this.setDate(jSonInfo.getJSONObject("createdDate"));
			this.setNote(Utilities.getDataString(jSonInfo, "note"));
			this.setStatus(Utilities.getDataString(jSonInfo, "status"));

		}

		public void setDate(JSONObject jSonInfo) throws JSONException {
			this.setCreateDate(Utilities.getDataString(jSonInfo, "date"));
		}

		public String getFullName() {
			return fullName;
		}

		public void setFullName(String fullName) {
			this.fullName = fullName;
		}

		public String getPhoneNumber() {
			return phoneNumber;
		}

		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}

		public String getRechargeTime() {
			return rechargeTime;
		}

		public void setRechargeTime(String rechargeTime) {
			this.rechargeTime = rechargeTime;
		}

		public String getCreateDate() {
			return createDate;
		}

		public void setCreateDate(String createDate) {
			this.createDate = createDate;
		}

		public String getNote() {
			return note;
		}

		public void setNote(String note) {
			this.note = note;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

	}

	public class PercentServiceModel implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String name;
		private String typeservice;
		private String percent;

		public void setData(JSONObject jSonInfo) throws JSONException {
			this.setName(Utilities.getDataString(jSonInfo, "name"));
			this.setTypeservice(Utilities.getDataString(jSonInfo, "service"));
			this.setPercent(Utilities.getDataString(jSonInfo, "percent"));

		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getTypeservice() {
			return typeservice;
		}

		public void setTypeservice(String typeservice) {
			this.typeservice = typeservice;
		}

		public String getPercent() {
			return percent;
		}

		public void setPercent(String percent) {
			this.percent = percent;
		}

	}

	public class UserModel implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String firstname;
		private String username;
		private String lastname;
		private String IdUser;
		private String email;
		private String phone;
		private String score;

		public void setData(JSONObject jSonInfo) throws JSONException {
			this.setUsername(Utilities.getDataString(jSonInfo, "Username"));
			this.setFirstname(Utilities.getDataString(jSonInfo, "FristName"));
			this.setLastname(Utilities.getDataString(jSonInfo, "LastName"));
			this.setIdUser(Utilities.getDataString(jSonInfo, "UserID"));
			this.setEmail(Utilities.getDataString(jSonInfo, "Email"));
			this.setPhone(Utilities.getDataString(jSonInfo, "Phone"));
			this.setScore(Utilities.getDataString(jSonInfo, "Score"));

		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String _username) {
			username = _username;
		}

		public String getFirstname() {
			return firstname;
		}

		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}

		public String getLastname() {
			return lastname;
		}

		public void setLastname(String lastname) {
			this.lastname = lastname;
		}

		public String getIdUser() {
			return IdUser;
		}

		public void setIdUser(String idUser) {
			IdUser = idUser;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getScore() {
			return score;
		}

		public void setScore(String score) {
			if (score.equals("null")) {
				this.score = "0";
			} else {
				this.score = score;
			}

		}

	}

	public class LogGameModel implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String loaigiaodich;
		private String nhamang;
		private String sdtPin;
		private String menhgia;
		private String trangthai;
		private String buyerreal;

		private String date;
		private String timezone;
		private String timezone_type;

		public void setData(JSONObject jSonInfo) throws JSONException {
			this.setLoaigiaodich(Utilities
					.getDataString(jSonInfo, "trans_type"));
			this.setNhamang(Utilities.getDataString(jSonInfo, "provider"));
			this.setSdtPin(Utilities.getDataString(jSonInfo, "buyer"));
			this.setDate(jSonInfo.getJSONObject("time_create"));
			this.setMenhgia(Utilities.getDataString(jSonInfo, "trans_price"));
			this.setTrangthai(Utilities.getDataString(jSonInfo, "status"));
			this.setBuyerreal(Utilities.getDataString(jSonInfo, "buyerreal"));

		}

		public void setDate(JSONObject jSonInfo) throws JSONException {
			this.setDate(Utilities.getDataString(jSonInfo, "date"));
			this.setTimezone(Utilities.getDataString(jSonInfo, "timezone"));
			this.setTimezone_type(Utilities.getDataString(jSonInfo,
					"timezone_type"));
		}

		public String getLoaigiaodich() {
			return loaigiaodich;
		}

		public void setLoaigiaodich(String loaigiaodich) {
			this.loaigiaodich = loaigiaodich;
		}

		public String getNhamang() {
			return nhamang;
		}

		public void setNhamang(String nhamang) {
			this.nhamang = nhamang;
		}

		public String getSdtPin() {
			return sdtPin;
		}

		public void setSdtPin(String sdtPin) {
			this.sdtPin = sdtPin;
		}

		public String getMenhgia() {
			return menhgia;
		}

		public void setMenhgia(String menhgia) {
			this.menhgia = menhgia;
		}

		public String getTrangthai() {
			return trangthai;
		}

		public void setTrangthai(String trangthai) {
			this.trangthai = trangthai;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getTimezone() {
			return timezone;
		}

		public void setTimezone(String timezone) {
			this.timezone = timezone;
		}

		public String getTimezone_type() {
			return timezone_type;
		}

		public void setTimezone_type(String timezone_type) {
			this.timezone_type = timezone_type;
		}

		public String getBuyerreal() {
			return buyerreal;
		}

		public void setBuyerreal(String buyerreal) {
			this.buyerreal = buyerreal;
		}

	}

	public class ListReservationModel implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String nameRes;
		private String name;
		private String china;
		private String chinaRes;
		private String rescatID;
		private String resid;

		public void ListReservationModel() {

		}

		public void setData(JSONObject jSonInfo) throws JSONException {
			this.setnameRes(Utilities.getDataString(jSonInfo, "nameRes"));
			this.setName(Utilities.getDataString(jSonInfo, "Name"));

			this.setChina(Utilities.getDataString(jSonInfo, "China"));
			this.setChinaRes(Utilities.getDataString(jSonInfo, "chinaRes"));
			this.setRescatID(Utilities.getDataString(jSonInfo, "rescatId"));
			this.setResid(Utilities.getDataString(jSonInfo, "resId"));
		}

		public String getnameRes() {
			return nameRes;
		}

		public void setnameRes(String resId) {
			this.nameRes = resId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getChina() {
			return china;
		}

		public void setChina(String china) {
			this.china = china;
		}

		public String getChinaRes() {
			return chinaRes;
		}

		public void setChinaRes(String chinaRes) {
			this.chinaRes = chinaRes;
		}

		public String getRescatID() {
			return rescatID;
		}

		public void setRescatID(String rescatID) {
			this.rescatID = rescatID;
		}

		public String getResid() {
			return resid;
		}

		public void setResid(String resid) {
			this.resid = resid;
		}

	}

	public class ContactModel implements Serializable {
		private static final long serialVersionUID = 1L;
		private String About_US;
		private String Image_Logo;
		private String Promotion_Message;

		public void setData(JSONObject jSonInfo) throws JSONException {
			this.setAbout_US(Utilities.getDataString(jSonInfo, "About_US"));
			this.setImage_Logo(Utilities.getDataString(jSonInfo, "Image_Logo"));
			this.setPromotion_Message(Utilities.getDataString(jSonInfo,
					"Promotion_Message"));

		}

		public String getAbout_US() {
			return About_US;
		}

		public void setAbout_US(String about_US) {
			About_US = about_US;
		}

		public String getImage_Logo() {
			return Image_Logo;
		}

		public void setImage_Logo(String image_Logo) {
			Image_Logo = image_Logo;
		}

		public String getPromotion_Message() {
			return Promotion_Message;
		}

		public void setPromotion_Message(String promotion_Message) {
			Promotion_Message = promotion_Message;
		}

	}

	public class RoomModel implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String idRoom;
		private String roomNumber;

		public void ListReservationModel() {

		}

		public void setData(JSONObject jSonInfo) throws JSONException {
			this.setIdRoom(Utilities.getDataString(jSonInfo, "RoomID"));
			this.setRoomNumber(Utilities.getDataString(jSonInfo, "Room_Number"));

		}

		public String getIdRoom() {
			return idRoom;
		}

		public void setIdRoom(String idRoom) {
			this.idRoom = idRoom;
		}

		public String getRoomNumber() {
			return roomNumber;
		}

		public void setRoomNumber(String roomNumber) {
			this.roomNumber = roomNumber;
		}

	}

	public class AdsModel implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String idAds;
		private String name;
		private String images;
		private String contentAds;
		private String nameChina;
		private String contentAdsChina;

		public void ListReservationModel() {

		}

		public void setData(JSONObject jSonInfo) throws JSONException {
			this.setImages(Utilities.getDataString(jSonInfo, "images"));
			this.setName(Utilities.getDataString(jSonInfo, "name"));
			this.setIdAds(Utilities.getDataString(jSonInfo, "id"));
			this.setContentAds(Utilities.getDataString(jSonInfo, "contentAds"));
			this.setNameChina(Utilities.getDataString(jSonInfo, "nameChina"));
			this.setContentAdsChina(Utilities.getDataString(jSonInfo,
					"contentAdsChina"));

		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getImages() {
			return images;
		}

		public void setImages(String images) {
			this.images = images;
		}

		public String getIdAds() {
			return idAds;
		}

		public void setIdAds(String idAds) {
			this.idAds = idAds;
		}

		public String getContentAds() {
			return contentAds;
		}

		public void setContentAds(String contentAds) {
			this.contentAds = contentAds;
		}

		public String getNameChina() {
			return nameChina;
		}

		public void setNameChina(String nameChina) {
			this.nameChina = nameChina;
		}

		public String getContentAdsChina() {
			return contentAdsChina;
		}

		public void setContentAdsChina(String contentAdsChina) {
			this.contentAdsChina = contentAdsChina;
		}

	}

	public class GROModel implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String GroID;
		private String FullName;
		private String Nationality;
		private String images;
		private String Height;
		private String Weight;
		private String Language;
		private String Status_Booking;

		public void GROModel() {

		}

		public void setData(JSONObject jSonInfo) throws JSONException {
			this.setGroID(Utilities.getDataString(jSonInfo, "GroID"));
			this.setFullName(Utilities.getDataString(jSonInfo, "FullName"));
			this.setImages(Utilities.getDataString(jSonInfo, "images"));
			this.setNationality(Utilities
					.getDataString(jSonInfo, "Nationality"));
			this.setHeight(Utilities.getDataString(jSonInfo, "Height"));
			this.setWeight(Utilities.getDataString(jSonInfo, "Weight"));
			this.setLanguage(Utilities.getDataString(jSonInfo, "Language"));
			this.setStatus_Booking(Utilities.getDataString(jSonInfo,
					"Status_Booking"));

		}

		public String getImages() {
			return images;
		}

		public void setImages(String images) {
			this.images = images;
		}

		public String getGroID() {
			return GroID;
		}

		public void setGroID(String groID) {
			GroID = groID;
		}

		public String getFullName() {
			return FullName;
		}

		public void setFullName(String fullName) {
			FullName = fullName;
		}

		public String getNationality() {
			return Nationality;
		}

		public void setNationality(String nationality) {
			Nationality = nationality;
		}

		public String getHeight() {
			return Height;
		}

		public void setHeight(String height) {
			Height = height;
		}

		public String getWeight() {
			return Weight;
		}

		public void setWeight(String weight) {
			Weight = weight;
		}

		public String getLanguage() {
			return Language;
		}

		public void setLanguage(String language) {
			Language = language;
		}

		public String getStatus_Booking() {
			return Status_Booking;
		}

		public void setStatus_Booking(String status_Booking) {
			Status_Booking = status_Booking;
		}

	}


	public class AllGROModel implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String GroID;
		private String FullName;
		private String Nationality;
		private String images;
		private String Height;
		private String Weight;
		private String Language;
		private String Status_Booking;

		public void GROModel() {

		}

		public void setData(JSONObject jSonInfo) throws JSONException {
			this.setGroID(Utilities.getDataString(jSonInfo, "GroID"));
			this.setFullName(Utilities.getDataString(jSonInfo, "FullName"));
			this.setImages(Utilities.getDataString(jSonInfo, "images"));
			this.setNationality(Utilities
					.getDataString(jSonInfo, "Nationality"));
			this.setHeight(Utilities.getDataString(jSonInfo, "Height"));
			this.setWeight(Utilities.getDataString(jSonInfo, "Weight"));
			this.setLanguage(Utilities.getDataString(jSonInfo, "Language"));
			this.setStatus_Booking(Utilities.getDataString(jSonInfo,
					"Status_Booking"));

		}

		public String getImages() {
			return images;
		}

		public void setImages(String images) {
			this.images = images;
		}

		public String getGroID() {
			return GroID;
		}

		public void setGroID(String groID) {
			GroID = groID;
		}

		public String getFullName() {
			return FullName;
		}

		public void setFullName(String fullName) {
			FullName = fullName;
		}

		public String getNationality() {
			return Nationality;
		}

		public void setNationality(String nationality) {
			Nationality = nationality;
		}

		public String getHeight() {
			return Height;
		}

		public void setHeight(String height) {
			Height = height;
		}

		public String getWeight() {
			return Weight;
		}

		public void setWeight(String weight) {
			Weight = weight;
		}

		public String getLanguage() {
			return Language;
		}

		public void setLanguage(String language) {
			Language = language;
		}

		public String getStatus_Booking() {
			return Status_Booking;
		}

		public void setStatus_Booking(String status_Booking) {
			Status_Booking = status_Booking;
		}

	}


	public static String FORGOTPASS = "forgotPass";

	public void callForgotPass(Activity cont,
			List<NameValuePair> JSONObjectParams, Boolean _isShowProgressBar) {
		message = "Not Message!";
		// Log.v("HAI", "param: " + JSONObjectParams);
		JSONMethod method = new JSONMethod(cont, JSONObjectParams, forgotPass,
				_isShowProgressBar);
		// method.execute();
	}

	JSONCallBack forgotPass = new JSONCallBack() {

		@Override
		public void callResult(Context activity, String result, long time) {
			try {
				JSONObject resultJson = new JSONObject(result);
				// JSONObject objectResult = resultJson.getJSONObject("result");
				message = Utilities.getDataString(resultJson, "message");
				if (Boolean.parseBoolean(Utilities.getDataString(resultJson,
						"is_show_message"))) {
					isShowMessage = true;
				}
				if (null != result) {
					if ((Boolean) resultJson.get("is_success")) {
						sendMessage(activity, FORGOTPASS, true);
					} else {
						sendMessage(activity, FORGOTPASS, false);
					}

				} else {
					sendMessage(activity, FORGOTPASS, false);
				}
			} catch (Exception e) {
				// TODO: handle exception
				sendMessage(activity, FORGOTPASS, false);
			}

		}
	};
}
