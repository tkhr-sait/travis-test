Feature: SwaggerEditorでgitユーザの管理を行いたい。
 操作者は、エディタ画面での操作者を把握するために、ユーザ管理を行う必要がある。

Scenario:ユーザの登録
 Given "http://127.0.0.1:9700/editor" URLを開く
  When "10_editor" スクリーンショットをとる
  When "User:" をクリックする
   And "Add User" をクリックする
  Then "Add User" ダイアログが表示される
  When "id:" に "test" を入力する
   And "email:" に "test@example.com" を入力する
  When "20_Add User" スクリーンショットをとる
  When "Create" をクリックする

