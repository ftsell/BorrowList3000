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
 * @interface UserDto
 */
export interface UserDto {
  /**
   *
   * @type {string}
   * @memberof UserDto
   */
  username: string;
  /**
   *
   * @type {string}
   * @memberof UserDto
   */
  email: string;
}

export function UserDtoFromJSON(json: any): UserDto {
  return UserDtoFromJSONTyped(json, false);
}

export function UserDtoFromJSONTyped(
  json: any,
  ignoreDiscriminator: boolean
): UserDto {
  if (json === undefined || json === null) {
    return json;
  }
  return {
    username: json["username"],
    email: json["email"],
  };
}

export function UserDtoToJSON(value?: UserDto | null): any {
  if (value === undefined) {
    return undefined;
  }
  if (value === null) {
    return null;
  }
  return {
    username: value.username,
    email: value.email,
  };
}
