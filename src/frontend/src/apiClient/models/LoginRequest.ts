/* tslint:disable */
/* eslint-disable */
/**
 * Thing-People-List Api
 * Public API of the Thing-People-List Service. Thing-People-List is a Web-Service to keep track of which people did what i.e. who borrowed what from you.
 *
 * The version of the OpenAPI document: 2.0.0
 * Contact: dev@finn-thorben.me
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import { exists, mapValues } from "../runtime";
/**
 *
 * @export
 * @interface LoginRequest
 */
export interface LoginRequest {
  /**
   *
   * @type {string}
   * @memberof LoginRequest
   */
  username: string;
  /**
   *
   * @type {string}
   * @memberof LoginRequest
   */
  password: string;
}

export function LoginRequestFromJSON(json: any): LoginRequest {
  return LoginRequestFromJSONTyped(json, false);
}

export function LoginRequestFromJSONTyped(
  json: any,
  ignoreDiscriminator: boolean
): LoginRequest {
  if (json === undefined || json === null) {
    return json;
  }
  return {
    username: json["username"],
    password: json["password"],
  };
}

export function LoginRequestToJSON(value?: LoginRequest | null): any {
  if (value === undefined) {
    return undefined;
  }
  if (value === null) {
    return null;
  }
  return {
    username: value.username,
    password: value.password,
  };
}