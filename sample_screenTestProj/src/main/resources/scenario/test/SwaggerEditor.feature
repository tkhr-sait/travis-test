Feature: SwaggerEditorでgitユーザの管理を行いたい。
 操作者は、エディタ画面での操作者を把握するために、ユーザ管理を行う必要がある。

Scenario:ユーザの登録
 Given "http://127.0.0.1:9700/editor" URLを開く
  When "10_editor" スクリーンショットをとる
