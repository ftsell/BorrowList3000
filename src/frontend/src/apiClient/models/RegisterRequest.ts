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
 * @interface RegisterRequest
 */
export interface RegisterRequest {
  /**
   *
   * @type {string}
   * @memberof RegisterRequest
   */
  username: string;
  /**
   *
   * @type {string}
   * @memberof RegisterRequest
   */
  password: string;
  /**
   *
   * @type {string}
   * @memberof RegisterRequest
   */
  email?: string;
}

export function RegisterRequestFromJSON(json: any): RegisterRequest {
  return RegisterRequestFromJSONTyped(json, false);
}

export function RegisterRequestFromJSONTyped(
  json: any,
  ignoreDiscriminator: boolean
): RegisterRequest {
  if (json === undefined || json === null) {
    return json;
  }
  return {
    username: json["username"],
    password: json["password"],
    email: !exists(json, "email") ? undefined : json["email"],
  };
}

export function RegisterRequestToJSON(value?: RegisterRequest | null): any {
  if (value === undefined) {
    return undefined;
  }
  if (value === null) {
    return null;
  }
  return {
    username: value.username,
    password: value.password,
    email: value.email,
  };
}
